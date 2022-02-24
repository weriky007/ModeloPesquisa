package com.alphazer0.pesquisaexemplo;
//==================================================================================================
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.alphazer0.pesquisaexemplo.database.ClientesDatabase;
import com.alphazer0.pesquisaexemplo.database.roomDAO.RoomClienteDAO;
import com.alphazer0.pesquisaexemplo.model.Cliente;
import com.alphazer0.pesquisaexemplo.ui.activities.clientes.CadastroClienteActivity;
import com.alphazer0.pesquisaexemplo.ui.adapters.ListaClientesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
//==================================================================================================
public class MainActivity extends AppCompatActivity {
    private ListView listaClientes;
    private FloatingActionButton btnNovoCliente;
    private ListaClientesAdapter adapter;
    ArrayAdapter<Cliente> adapter2;
    private RoomClienteDAO dao;
    private List<Cliente> clientes = new ArrayList<>();
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

//        adicionaClientesTest();
        configuraAdapter();
        configuraLista();
        configuraFabNovoCliente();
    }

//==================================================================================================
    private void adicionaClientesTest() {
     Cliente cliente1 = new Cliente(0,"aa","99","aa");
     Cliente cliente2 = new Cliente(1,"bb","99","aa");
     Cliente cliente3 = new Cliente(2,"cc","99","aa");
     clientes.add(0,cliente1);
     clientes.add(1,cliente2);
     clientes.add(2,cliente3);
    }
//==================================================================================================
    @Override
    protected void onResume() {
        super.onResume();
        adapter.atualiza(dao.todosClientes());
    }
//==================================================================================================
    private void configuraAdapter() {
        adapter = new ListaClientesAdapter(clientes);
        adapter2 = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, clientes);
        //PEGA TODOS OS CLIENTES DO BANCO DE DADOS
        dao = ClientesDatabase.getInstance(this).getClienteDAO();
    }

    private void configuraLista() {
        listaClientes = findViewById(R.id.list_view_clientes);
        listaClientes.setAdapter(adapter);
    }

    private void configuraFabNovoCliente() {
        btnNovoCliente = findViewById(R.id.fab_novo_cliente);
        btnNovoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CadastroClienteActivity.class));
            }
        });
    }
//==================================================================================================
    //CONFIGURACOES DO FILTRO PESQUISA
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_search, menu);

    MenuItem searchItem = menu.findItem(R.id.action_search);
    SearchView campoBusca = (SearchView) searchItem.getActionView();

    campoBusca.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
    campoBusca.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (newText.toString() != null && !newText.toString().equals("")) {
                adapter.getFilter().filter(newText);
            }else {
                adapter.atualiza(dao.todosClientes());
            }
            return false;
        }
    });
    return true;
   }
//==================================================================================================
}