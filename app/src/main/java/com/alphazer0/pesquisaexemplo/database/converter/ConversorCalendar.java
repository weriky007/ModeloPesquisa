package com.alphazer0.pesquisaexemplo.database.converter;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class ConversorCalendar {
    //IMPLEMENTAR OS DOIS METODOS RESPONSAVEIS PELA CONVERSAO
    //UM PEGA O VALOR DO OBJETO CALENDAR E CONVERTE PARA O TIPO COMPATIVEL COM O BD
    //O OUTRO PEGA E FAZ O INVERSO

    //CONVERTE PARA O BD
    @TypeConverter
    public Long paraLong (Calendar valor){
        return valor.getTimeInMillis();
    }

    //CONVERTE PARA O OBJETO
    @TypeConverter
    public  Calendar paraCalendar(Long valor){
        Calendar instance = Calendar.getInstance();

        if(valor != null) {
            instance.setTimeInMillis(valor);
        }
        return instance;
    }

}
