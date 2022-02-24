package com.alphazer0.pesquisaexemplo.ui.activities.clientes;
//==================================================================================================
import static com.alphazer0.pesquisaexemplo.ui.activities.ConstantesActivities.CHAVE_CLIENTE;
import static com.alphazer0.pesquisaexemplo.ui.activities.ConstantesActivities.CLIENTE_PLAN;
import static com.alphazer0.pesquisaexemplo.ui.activities.ConstantesActivities.ID_PASTA;
import static com.alphazer0.pesquisaexemplo.ui.activities.ConstantesActivities.LINK_MACRO;
import static com.alphazer0.pesquisaexemplo.ui.activities.ConstantesActivities.MASK_CEL;
import static com.alphazer0.pesquisaexemplo.ui.activities.ConstantesActivities.MASK_CEP;
import static com.alphazer0.pesquisaexemplo.ui.activities.ConstantesActivities.MASK_CPF;
import static com.alphazer0.pesquisaexemplo.ui.activities.ConstantesActivities.MASK_DATA_NASCIMENTO;
import static com.alphazer0.pesquisaexemplo.ui.activities.ConstantesActivities.MASK_TEL;
import static com.alphazer0.pesquisaexemplo.ui.activities.ConstantesActivities.TITULO_APPBAR_CADASTRO_CLIENTE;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alphazer0.pesquisaexemplo.MainActivity;
import com.alphazer0.pesquisaexemplo.R;
import com.alphazer0.pesquisaexemplo.database.ClientesDatabase;
import com.alphazer0.pesquisaexemplo.database.roomDAO.RoomClienteDAO;
import com.alphazer0.pesquisaexemplo.model.Cliente;
import com.alphazer0.pesquisaexemplo.model.MaskText;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
//==================================================================================================
public class CadastroClienteActivity extends AppCompatActivity {
    public CadastroClienteActivity() throws SQLException {
    }

    @Override
    protected void onCreate(Bundle saveIntanceState) {
        ActionBar bar = getSupportActionBar();
//        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFB0000")));
        super.onCreate(saveIntanceState);
        setTitle(TITULO_APPBAR_CADASTRO_CLIENTE);
        setContentView(R.layout.activity_formulario_dados_cliente);

        ClientesDatabase dataBase = ClientesDatabase.getInstance(this);
        dao = dataBase.getClienteDAO();

        bindDosCampos();
        carregaCliente();
        formataTexto();
        configuraBotao();
    }
//==================================================================================================
    //REFERENCIANDO OS ELEMENTOS
    private Cliente cliente;
    private RoomClienteDAO dao;


    //CONFIGURACAO SCRIPT E PLANILHA BASE DADOS
    String linkMacro= LINK_MACRO;
    String idPlanilha = ID_PASTA;
    int put = 0;

    private List<Cliente> clientes = new ArrayList<>();
    public int id;
    //DADOS PESSOAIS
    private EditText campoNomeCompleto;
    //CONTATO
    private EditText campoCelular1;
    //ENDERECO
    private EditText campoRua;
    private Button botao;
//==================================================================================================
    private void bindDosCampos() {
        campoNomeCompleto = findViewById(R.id.edit_razao_social);
        campoCelular1 = findViewById(R.id.edit_campo_cel1);
        campoRua = findViewById(R.id.edit_campo_rua);
        botao = findViewById(R.id.botao_salvar);
    }
//==================================================================================================
    //MASCARA FORMATA TEXTO
    private void formataTexto() {
        campoCelular1.addTextChangedListener(MaskText.insert(MASK_CEL, campoCelular1));
    }
//==================================================================================================
    private void recebeDadosDigitadosNosCampos() {
        String nomeCompleto = campoNomeCompleto.getText().toString();
        String celular1 = campoCelular1.getText().toString();
        String rua = campoRua.getText().toString();

        cliente.setNomeCompleto(nomeCompleto);
        cliente.setCelular1(celular1);
        cliente.setRua(rua);
    }
//==================================================================================================
    private void carregaCliente() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_CLIENTE)) {
            cliente = (Cliente) dados.getSerializableExtra(CHAVE_CLIENTE);
            preencheCamposParaEdicao();
        } else {
            cliente = new Cliente();
        }
    }
//==================================================================================================
    private void preencheCamposParaEdicao() {
        campoNomeCompleto.setText(cliente.getNomeCompleto());
        campoCelular1.setText(cliente.getCelular1());
        campoRua.setText(cliente.getRua());
    }
//==================================================================================================
    private void concluiCadastro()  {
        recebeDadosDigitadosNosCampos();
        if (cliente.temIdValido()) {
            put = 2;
            dao.editaCliente(cliente);
            clientes = dao.todosClientes();
            Toast.makeText(CadastroClienteActivity.this, "Editado com Sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            put = 1;
            dao.salvaCliente(cliente);
            clientes = dao.todosClientes();
            Toast.makeText(CadastroClienteActivity.this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
        }
    }
//==================================================================================================
//INICIANDO COMUNICACAO WEB
//public class SendRequest extends AsyncTask<String, Void, String> {
//
//    protected void onPreExecute(){}
//
//    protected String doInBackground(String... arg0) {
//        try{
//            URL url = new URL(linkMacro);
//            String idPlan= idPlanilha;
//
//            JSONObject enviaDados = enviaDadosParaPlanilha(idPlan);
//            HttpURLConnection connection = executaConeccaoExternalServer(url);
//            escreveDadosNaPlanilha(enviaDados, connection);
//            return verificaLinhaVazia(connection);
//        }//end try
//
//        catch(Exception e){
//            return new String("Exception: " + e.getMessage());
//        }//end catch
//
//    }//end doInBackGround
//
//    @Override
//    protected void onPostExecute(String resultado) {
//        Toast.makeText(getApplicationContext(),"Salvo Na GSheet!!!",Toast.LENGTH_LONG).show();
////            Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
//    }//end onPostExecute
//}//end sendRequest
////==================================================================================================
//    private String verificaLinhaVazia(HttpURLConnection connection) throws IOException {
//        int codigoWeb = connection.getResponseCode();
//        if (codigoWeb == HttpsURLConnection.HTTP_OK) {
//            BufferedReader leValor = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            StringBuffer sb = new StringBuffer("");
//            String linha="";
//
//            while((linha = leValor.readLine()) != null) {
//                sb.append(linha);
//                break;
//            }//end while
//
//            leValor.close();
//            return sb.toString();
//
//        }//end if
//        else {
//            return new String("false : "+codigoWeb);
//        }//end else
//    }
//
//    private void escreveDadosNaPlanilha(JSONObject enviaDados, HttpURLConnection connection) throws Exception {
//        OutputStream saida = connection.getOutputStream();
//        BufferedWriter escrita = new BufferedWriter(new OutputStreamWriter(saida, "UTF-8"));
//
//        escrita.write(configuraDadosParaWeb(enviaDados));
//        escrita.flush();
//        escrita.close();
//        saida.close();
//    }
//
//    private HttpURLConnection executaConeccaoExternalServer(URL url) throws IOException {
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setReadTimeout(15000 /* milliseconds */);
//        connection.setConnectTimeout(15000 /* milliseconds */);
//        connection.setRequestMethod("POST");
//        connection.setDoInput(true);
//        connection.setDoOutput(true);
//        return connection;
//    }
//
//    //ENVIA DADOS PARA A PLANILHA GOOGLE
//    private JSONObject enviaDadosParaPlanilha(String action) throws JSONException {
//        JSONObject enviaDados = new JSONObject();
//        if(put == 1) {
//            action = "addCliente";
//           cliente = clientes.get(clientes.size()-1);
//           id = cliente.getId();
//        }
//        if(put == 2){
//            action ="updateCliente";
//            id = cliente.getId();
//        }
//
//        enviaDados.put("pasta", ID_PASTA);
//        enviaDados.put("planilha",CLIENTE_PLAN);
//        enviaDados.put("action",action);
//        enviaDados.put("idCliente",id);
//        enviaDados.put("nomeCompleto", cliente.getNomeCompleto());
//        enviaDados.put("dataNascimento", cliente.getDataNascimento());
//        enviaDados.put("cpf",cliente.getCpf());
//        enviaDados.put("rg",cliente.getRg());
//        enviaDados.put("nomePai",cliente.getNomePai());
//        enviaDados.put("nomeMae",cliente.getNomeMae());
//
//        enviaDados.put("celular1",cliente.getCelular1());
//        enviaDados.put("celular2",cliente.getCelular2());
//        enviaDados.put("telefone",cliente.getTelefone());
//        enviaDados.put("email",cliente.getEmail());
//
//        enviaDados.put("cep",cliente.getCep());
//        enviaDados.put("rua",cliente.getRua());
//        enviaDados.put("numero",cliente.getNumero());
//        enviaDados.put("quadra",cliente.getQuadra());
//        enviaDados.put("lote",cliente.getLote());
//        enviaDados.put("bairro",cliente.getBairro());
//        enviaDados.put("complemento",cliente.getComplemento());
//
//        Log.e("params",enviaDados.toString());
//        return enviaDados;
//    }
//
//    public String configuraDadosParaWeb(JSONObject params) throws Exception {
//        StringBuilder result = new StringBuilder();
//        boolean first = true;
//
//        Iterator<String> itr = params.keys();
//
//        while(itr.hasNext()){
//            String key= itr.next();
//            Object value = params.get(key);
//
//            if (first) {
//                first = false;
//            }else {
//                result.append("&");
//            }
//            result.append(URLEncoder.encode(key, "UTF-8"));
//            result.append("=");
//            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
//
//        }
//        return result.toString();
//    }//end configuraData
//==================================================================================================
    private void configuraBotao() {
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concluiCadastro();
//                new SendRequest().execute();
                startActivity(new Intent(CadastroClienteActivity.this, MainActivity.class));
                finish();
            }
        });
    }
//==================================================================================================
}

