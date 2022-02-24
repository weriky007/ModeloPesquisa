package com.alphazer0.pesquisaexemplo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.alphazer0.pesquisaexemplo.database.converter.ConversorCalendar;
import com.alphazer0.pesquisaexemplo.database.roomDAO.RoomClienteDAO;
import com.alphazer0.pesquisaexemplo.model.Cliente;

@Database(entities = {Cliente.class},version = 1, exportSchema = false)

//ADICIONANDO OS TIPOS DE CONVERSOR QUE VAMOS TER NO PROJETO
@TypeConverters({ConversorCalendar.class})
public abstract class ClientesDatabase extends RoomDatabase {
    /*CASO ADICIONE UM CAMPO A MAIS OU REMOVA DO FORMULARIO,
    UTILIZE O ".fallbackToDestructiveMigration()" QUE IRA DESTRUIR O BD E RECRIAR UM NOVO COM OS NOVOS
    DADOS. MAS SO FACA ISSO QUANDO NINGUEM ESTIVER USANDO O APLICATIVO, SOMENTE EM PRODUCAO SE NAO
    IRA APAGAR TODOS OS DADOS.
    
     */

    private static final String NOME_BD = "clientes.db";
    public abstract RoomClienteDAO getClienteDAO();

    public static ClientesDatabase getInstance(Context context){
        return Room.databaseBuilder(context, ClientesDatabase.class, NOME_BD)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
