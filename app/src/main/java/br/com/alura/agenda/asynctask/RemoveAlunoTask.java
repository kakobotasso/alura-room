package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;

import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.ui.adapter.ListaAlunosAdapter;

public class RemoveAlunoTask extends AsyncTask<Void, Void, Void> {
    private final AlunoDAO dao;
    private final Aluno aluno;
    private final ListaAlunosAdapter adapter;

    public RemoveAlunoTask(AlunoDAO dao, Aluno aluno, ListaAlunosAdapter adapter) {
        this.dao = dao;
        this.aluno = aluno;
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.remove(aluno);
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        adapter.remove(aluno);
    }
}
