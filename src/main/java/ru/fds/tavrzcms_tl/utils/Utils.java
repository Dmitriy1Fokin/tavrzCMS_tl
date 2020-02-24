package ru.fds.tavrzcms_tl.utils;

import org.springframework.stereotype.Component;
import ru.fds.tavrzcms_tl.dictionary.Operations;
import ru.fds.tavrzcms_tl.dictionary.TypeOfClient;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class Utils {

    public Map<String, String> ExtractClientName(Map<String, String> clientNameAttr){
        if(Objects.isNull(clientNameAttr.get("clientName"))){
            return Collections.emptyMap();
        }

        Map<String, String > extractName = new HashMap<>();
        if(clientNameAttr.get("typeOfClient").equals(TypeOfClient.LEGAL_ENTITY.name())){
            extractName.put("typeOfClient", TypeOfClient.LEGAL_ENTITY.name());
            extractName.put("name", clientNameAttr.get("clientName"));
            extractName.put("nameOption", Operations.EQUAL_IGNORE_CASE.name());
        }else {
            extractName.put("typeOfClient", TypeOfClient.INDIVIDUAL.name());
            String[] words = clientNameAttr.get("clientName").split("\\s");
            if(words.length == 1){
                extractName.put("surname", words[0]);
                extractName.put("surnameOption", Operations.EQUAL_IGNORE_CASE.name());
            }else if(words.length ==2){
                extractName.put("surname", words[0]);
                extractName.put("surnameOption", Operations.EQUAL_IGNORE_CASE.name());
                extractName.put("name", words[1]);
                extractName.put("nameOption", Operations.EQUAL_IGNORE_CASE.name());
            }else if(words.length >2){
                extractName.put("surname", words[0]);
                extractName.put("surnameOption", Operations.EQUAL_IGNORE_CASE.name());
                extractName.put("name", words[1]);
                extractName.put("nameOption", Operations.EQUAL_IGNORE_CASE.name());
                extractName.put("patronymic", words[1]);
                extractName.put("patronymicOption", Operations.EQUAL_IGNORE_CASE.name());
            }
        }
        return extractName;
    }



}
