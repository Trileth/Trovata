package com.example.jeffe.produtos;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.List;

import com.example.jeffe.adapter.EmpresaAdapter;
import com.example.jeffe.dao.EmpresaDAO;
import com.example.jeffe.model.Empresa;
import com.example.jeffe.util.Mensagem;

public class EmpresasActivity extends Activity implements
        AdapterView.OnItemClickListener, DialogInterface.OnClickListener {

    private ListView lista;
    private List<Empresa> empresaList;
    private EmpresaDAO empresaDAO;

    private int idposicao;
    private int empresa;

    private android.app.AlertDialog alertDialog;
    private android.app.AlertDialog alertConfirmacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_empresas);

        alertDialog      = Mensagem.criarAlertDialog(this);
        alertConfirmacao = Mensagem.criarDialogConfirmacao(this);

        carregaListaEmpresas();
    }

    public void carregaListaEmpresas(){
        empresaDAO     = new EmpresaDAO(this);;

        empresa = getIntent().getIntExtra("emp",1);
        empresaList    = empresaDAO.listarEmpresas();
        EmpresaAdapter empresaAdapter = new EmpresaAdapter(this, empresaList);

        if(empresaList.size() > 0) {
            lista = findViewById(R.id.listaEmpresas);
            lista.setAdapter(empresaAdapter);
            lista.setOnItemClickListener(this);
        } else {
            Mensagem.Msg(this, getString(R.string.mensagem_nada_encontrado));
        }
    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empresas, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_cadastrar:
                Intent intent = new Intent(this, CadEmpresaActivity.class);
                intent.putExtra("EMP_LOGADA", empresa);
                startActivity(intent);
                break;
            case R.id.action_cancelar:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        int id = empresaList.get(idposicao).getId();
        switch (which){
            case 0:
                Intent intent = new Intent(this, CadEmpresaActivity.class);
                intent.putExtra("EMPRESA_ID", id);
                startActivity(intent);
                finish();
                break;
            case 1:
                alertConfirmacao.show();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                empresaList.remove(idposicao);
                empresaDAO.removerEmpresa(id);
                lista.invalidateViews();
                Mensagem.Msg(this, getString(R.string.mensagem_excluir));
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                alertConfirmacao.dismiss();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        idposicao = position;
        alertDialog.show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

