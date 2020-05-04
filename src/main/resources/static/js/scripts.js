
function setVisible(data) {
    let el = document.getElementById(data);
    if(el.style.display==="none"){
        el.style.display="block";
    }else {
        el.style.display="none";
    }
}

$(document).ready(function () {
    $("#inputSearchNumLA").submit(function (e) {
        e.preventDefault();
        const num = $("#numLA").val();
        $('#tBodyLA').html('');

        $.ajax({
            url : '/pledge_agreement/searchLA',
            type: 'GET',
            dataType: 'json',
            data : ({
                numLA: num
            }),
            success: function (loanAgreementList) {
                let el = document.getElementById("searchResultLA");
                el.style.display="block";
                for (const index in loanAgreementList) {
                    const pa = loanAgreementList[index];
                    $('#tBodyLA').append("<tr><td><input type=\"checkbox\" onclick='choise()' name=\"checkElement\" value='"+ pa['loanAgreementId']+ "'></td><td>" + pa['numLA'] + "</td><td>" + pa['dateBeginLA'] + "</td></tr>");
                }
            },
            error: function () {
                alert("Уппс!");
            }
        });
    });
});


function choise() {
    let checkBoxArray = document.getElementsByName('checkElement'),
        count = checkBoxArray.length - 1,
        isDisabled = true;
    for(;count>=0;count--){
        if (checkBoxArray[count]['checked']===true){
            isDisabled=!isDisabled;
            break;
        }
    }
    document.getElementById('buttonInsert').disabled = isDisabled;

}


function insertLA() {
    var loanAgreementIdArray = [];
    $('#tBodyLA input:checkbox:checked').each(function () {
        loanAgreementIdArray.push($(this).val());
    });
    const pledgeAgreementId = $("#pledgeAgreementId").text();

    $.ajax({
        url: '/pledge_agreement/insertLA',
        type: 'POST',
        dataType: 'json',
        data: ({
            loanAgreementIdArray: loanAgreementIdArray,
            pledgeAgreementId: pledgeAgreementId
        }),
        success: function () {
            location.reload();
        },
        error: function () {
            // alert("Ошибка при добавлении КД!");
            location.reload();
        }
    });
}


$(document).ready(function () {
    $("#inputSearchCadastralNum").submit(function (e) {
        e.preventDefault();
        const num = $("#cadastralNum").val();
        $('#tBodyPS').html('');

        $.ajax({
            url : '/pledge_agreement/searchPS/realty',
            type: 'GET',
            dataType: 'json',
            data : ({
                cadastralNum: num
            }),
            success: function (pledgeSubjectList) {
                let realty;
                let el = document.getElementById("searchResultPS");
                el.style.display="block";
                for (var index in pledgeSubjectList) {
                    const ps = pledgeSubjectList[index];
                    if(ps['typeOfCollateral'] === 'BUILDING'){
                        realty = ps['pledgeSubjectBuildingDto'];
                        $('#tBodyPS').append("<tr><td><input type=\"checkbox\" onclick='choise()' name=\"checkElement\" value='"+ ps['pledgeSubjectId']+ "'></td><td>" + ps['name'] + "</td><td>" + realty['cadastralNum'] + "</td></tr>");
                    }else if(ps['typeOfCollateral'] === 'LAND_LEASE'){
                        realty = ps['pledgeSubjectLandLeaseDto'];
                        $('#tBodyPS').append("<tr><td><input type=\"checkbox\" onclick='choise()' name=\"checkElement\" value='"+ ps['pledgeSubjectId']+ "'></td><td>" + ps['name'] + "</td><td>" + realty['cadastralNum'] + "</td></tr>");
                    }else if(ps['typeOfCollateral'] === 'LAND_OWNERSHIP'){
                        realty = ps['pledgeSubjectLandOwnershipDto'];
                        $('#tBodyPS').append("<tr><td><input type=\"checkbox\" onclick='choise()' name=\"checkElement\" value='"+ ps['pledgeSubjectId']+ "'></td><td>" + ps['name'] + "</td><td>" + realty['cadastralNum'] + "</td></tr>");
                    }else if(ps['typeOfCollateral'] === 'PREMISE'){
                        realty = ps['pledgeSubjectRoomDto'];
                        $('#tBodyPS').append("<tr><td><input type=\"checkbox\" onclick='choise()' name=\"checkElement\" value='"+ ps['pledgeSubjectId']+ "'></td><td>" + ps['name'] + "</td><td>" + realty['cadastralNum'] + "</td></tr>");
                    }
                }
            }
        });
    });


    $("#inputSearchNamePS").submit(function (e) {
        e.preventDefault();
        const num = $("#namePS").val();
        $('#tBodyPS').html('');

        $.ajax({
            url : '/pledge_agreement/searchPS/movables',
            type: 'GET',
            dataType: 'json',
            data : ({
                namePS: num
            }),
            success: function (pledgeSubjectList) {
                let psIn;
                let el = document.getElementById("searchResultPS");
                el.style.display="block";
                for (var index in pledgeSubjectList) {
                    var ps = pledgeSubjectList[index];
                    if(ps["typeOfCollateral"] === "AUTO"){
                        psIn = ps['pledgeSubjectAutoDto'];
                        $('#tBodyPS').append("<tr><td><input type=\"checkbox\" onclick='choise()' name=\"checkElement\" value='"+ ps['pledgeSubjectId']+ "'></td><td>" + ps['name'] + "</td><td>" + psIn['vin'] + "</td></tr>");
                    }else if(ps["typeOfCollateral"] === "EQUIPMENT"){
                        psIn = ps['pledgeSubjectEquipmentDto'];
                        $('#tBodyPS').append("<tr><td><input type=\"checkbox\" onclick='choise()' name=\"checkElement\" value='"+ ps['pledgeSubjectId']+ "'></td><td>" + ps['name'] + "</td><td>" + psIn['serialNum'] + "</td></tr>");
                    }else if(ps["typeOfCollateral"] === "SECURITIES"){
                        psIn = ps['pledgeSubjectSecuritiesDto'];
                        $('#tBodyPS').append("<tr><td><input type=\"checkbox\" onclick='choise()' name=\"checkElement\" value='"+ ps['pledgeSubjectId']+ "'></td><td>" + ps['name'] + "</td><td>" + psIn['nominalValue'] + "</td></tr>");
                    }else if(ps["typeOfCollateral"] === "VESSEL"){
                        psIn = ps['pledgeSubjectVesselDto'];
                        $('#tBodyPS').append("<tr><td><input type=\"checkbox\" onclick='choise()' name=\"checkElement\" value='"+ ps['pledgeSubjectId']+ "'></td><td>" + ps['name'] + "</td><td>" + psIn['imo'] + "</td></tr>");
                    }else if(ps["typeOfCollateral"] === "TBO"){
                        psIn = ps['pledgeSubjectTboDto'];
                        $('#tBodyPS').append("<tr><td><input type=\"checkbox\" onclick='choise()' name=\"checkElement\" value='"+ ps['pledgeSubjectId']+ "'></td><td>" + ps['name'] + "</td><td>" + psIn['carryingAmount'] + "</td></tr>");
                    }else if(ps["typeOfCollateral"] === "BUILDING"){
                        psIn = ps['pledgeSubjectBuildingDto'];
                        $('#tBodyPS').append("<tr><td><input type=\"checkbox\" onclick='choise()' name=\"checkElement\" value='"+ ps['pledgeSubjectId']+ "'></td><td>" + ps['name'] + "</td><td>" + psIn['cadastralNum'] + "</td></tr>");
                    }else if(ps["typeOfCollateral"] === "LAND_LEASE"){
                        psIn = ps['pledgeSubjectLandLeaseDto'];
                        $('#tBodyPS').append("<tr><td><input type=\"checkbox\" onclick='choise()' name=\"checkElement\" value='"+ ps['pledgeSubjectId']+ "'></td><td>" + ps['name'] + "</td><td>" + psIn['cadastralNum'] + "</td></tr>");
                    }else if(ps["typeOfCollateral"] === "LAND_OWNERSHIP"){
                        psIn = ps['pledgeSubjectLandOwnershipDto'];
                        $('#tBodyPS').append("<tr><td><input type=\"checkbox\" onclick='choise()' name=\"checkElement\" value='"+ ps['pledgeSubjectId']+ "'></td><td>" + ps['name'] + "</td><td>" + psIn['cadastralNum'] + "</td></tr>");
                    }else if(ps["typeOfCollateral"] === "PREMISE"){
                        psIn = ps['pledgeSubjectRoomDto'];
                        $('#tBodyPS').append("<tr><td><input type=\"checkbox\" onclick='choise()' name=\"checkElement\" value='"+ ps['pledgeSubjectId']+ "'></td><td>" + ps['name'] + "</td><td>" + psIn['cadastralNum'] + "</td></tr>");
                    }
                }
            }
        });
    });

});


function insertPS() {
    var pledgeSubjectsIdArray = [];
    $('#tBodyPS input:checkbox:checked').each(function () {
        pledgeSubjectsIdArray.push($(this).val());
    });
    const pledgeAgreementId = $("#pledgeAgreementId").text();

    $.ajax({
        url: '/pledge_agreement/pledge_subject/insert/exist',
        type: 'POST',
        dataType: 'json',
        data: {
            pledgeSubjectsIdArray: pledgeSubjectsIdArray,
            pledgeAgreementId: pledgeAgreementId
        },
        success: function () {
            location.reload();
        },
        error: function () {
            location.reload();
        }
    });
}

function withdrawFromDepositPledgeSubject(pledgeSubjectId, pledgeAgreementId) {
    $.ajax({
        url: '/pledge_agreement/withdrawFromDepositPledgeSubject',
        type: 'POST',
        dataType: 'json',
        data: {
            pledgeSubjectId: pledgeSubjectId,
            pledgeAgreementId: pledgeAgreementId
        },
        success: function () {
            location.reload();
        },
        error: function () {
            alert("!!!!!!!!!!!!!!!!!");
            location.reload();
        }
    });

}


$(document).ready(function () {
    $("#inputSearchClient").submit(function (e) {
        e.preventDefault();

        const n = document.getElementById("typeOfClient").options.selectedIndex;
        const typeOfClient = document.getElementById("typeOfClient").options[n].value;
        const nameClient = $("#nameClient").val();

        $('#tBodyClient').html('');

        $.ajax({
            url : '/employee/searchClient',
            type: 'GET',
            dataType: 'json',
            data : {
                typeOfClient : typeOfClient,
                nameClient : nameClient
            },
            success: function (clientList) {
                let el = document.getElementById("searchResultClient");
                el.style.display="block";
                for (const index in clientList) {
                    const client = clientList[index];
                    let clientName = '';
                    if(client['typeOfClient'] === 'LEGAL_ENTITY'){
                        const clientLegalEntity = client['clientLegalEntityDto'];
                        clientName = clientLegalEntity['organizationalForm'] + ' ' + clientLegalEntity['name'];
                    }else {
                        const clientIndividual = client['clientIndividualDto'];
                        clientName = clientIndividual['surname'] + ' ' + clientIndividual['name'] + ' ' + clientIndividual['patronymic'];
                    }

                    $('#tBodyClient').append("<tr><td><input type=\"checkbox\" onclick='choise()' name=\"checkElement\" value='"+ client['clientId']+ "'></td><td>" + clientName + "</td></tr>");
                }
            },
            error: function () {
                alert("Уппс!");
            }
        });
    });
});


function insertClientEscort() {
    var clientIdIdArray = [];
    $('#tBodyClient input:checkbox:checked').each(function () {
        clientIdIdArray.push($(this).val());
    });
    const employeeId = $("#employeeId").text();

    $.ajax({
        url: '/employee/insertClientEscort',
        type: 'POST',
        dataType: 'json',
        data: ({
            clientIdArray: clientIdIdArray,
            employeeId: employeeId
        }),
        success: function () {
            location.reload();
        },
        error: function () {
            location.reload();
        }
    });
}