package com.alphazer0.pesquisaexemplo.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.alphazer0.pesquisaexemplo.R;
import com.alphazer0.pesquisaexemplo.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ListaClientesAdapter extends BaseAdapter implements Filterable {
//==================================================================================================
    //CRIANDO UMA COPIA DA LISTA PARA O ADAPTER
    private  List<Cliente> clientePesquisa;
    private  List<Cliente> clientes;
    //private final Context context;

    public ListaClientesAdapter(List<Cliente> cliente) {
        //his.context = context;
        this.clientePesquisa = cliente;
        this.clientes = cliente;
    }
//==================================================================================================

    //INDICA A QUANTIDADE DE ELEMENTOS QUE O ADAPTER TERA
    @Override
    public int getCount() { return clientePesquisa.size();
    }

    //INDICA O ELEMENTO QUE VC QUER COM BASE NA POSICAO
    @Override
    public Cliente getItem(int position) {
        return clientePesquisa.get(position);
    }

    //ELEMENTO PELO ID, CASO NAO HOUVER ID NA LISTA DEIXE ZERO
    @Override
    public long getItemId(int position) {
        return clientePesquisa.get(position).getId();
    }

    //REPRESENTA AS CONFIGURACOES VIEW QUE SERA APRESENTADA
    @Override
    public View getView(int position, View convertView, ViewGroup listViewClientes) {
        //NAO PRECISA UTILISAR O FALSE QUANDO O LAYOUT E LINEAR
        View viewCriada = criaView(listViewClientes);

        Cliente clienteDevolvido = clientePesquisa.get(position);
        vincula(viewCriada, clienteDevolvido);

        return viewCriada;
    }

    private void vincula(View viewCriada, Cliente clienteDevolvido) {
        //BIND
        TextView nome = viewCriada.findViewById(R.id.item_cliente_nome);
        TextView celular1 = viewCriada.findViewById(R.id.item_cliente_telefone);
        TextView endereco = viewCriada.findViewById(R.id.item_cliente_endereco);

        nome.setText(clienteDevolvido.getNomeCompleto());
        celular1.setText(clienteDevolvido.getCelular1());
        endereco.setText("R :"+clienteDevolvido.getRua());
    }

    private View criaView(ViewGroup listViewClientes) {
        return LayoutInflater.from(listViewClientes.getContext()).inflate(R.layout.item_cliente, listViewClientes,false);
    }
//==================================================================================================
    public void atualiza(List<Cliente> cliente){
        this.clientes.clear();
        this.clientes.addAll(cliente);
        notifyDataSetChanged();
    }

    public void remove(Cliente cliente) {
        clientePesquisa.remove(cliente);
        notifyDataSetChanged();
    }
//==================================================================================================
    //INICIANDO CONFIGURACAO DO FILTRO
    @Override
    public Filter getFilter() {
    return filtroCliente;
    }

    //CONFIGURACAO DO FILTRO
    private Filter filtroCliente = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Cliente> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(clientes);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Cliente item : clientes) {
                    if (item.getNomeCompleto().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clientePesquisa.clear();
            clientePesquisa.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
//==================================================================================================
}
