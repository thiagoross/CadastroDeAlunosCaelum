package br.com.caelum.cadastro;

import android.widget.EditText;
import android.widget.RatingBar;

import br.com.caelum.cadastro.modelo.Aluno;

/**
 * Created by thiago on 1/30/16.
 */
public class FormularioHelper {

    private Aluno aluno;

    private EditText nome;
    private EditText telefone;
    private EditText endereco;
    private EditText site;
    private RatingBar nota;

    FormularioHelper(FormularioActivity activity) {
        this.aluno = new Aluno();

        this.nome = (EditText)activity.findViewById(R.id.formulario_nome);
        this.telefone = (EditText)activity.findViewById(R.id.formulario_telefone);
        this.endereco = (EditText)activity.findViewById(R.id.formulario_endereco);
        this.site = (EditText)activity.findViewById(R.id.formulario_site);
        this.nota = (RatingBar)activity.findViewById(R.id.formulario_nota);
    }

    public Aluno pegaAlunoDoFormulario() {
        aluno.setNome(nome.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));

        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        this.nome.setText(aluno.getNome());
        this.telefone.setText(aluno.getTelefone());
        this.endereco.setText(aluno.getEndereco());
        this.site.setText(aluno.getSite());
        this.nota.setProgress(aluno.getNota().intValue());

        this.aluno = aluno;
    }


}
