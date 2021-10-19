package br.com.alura.agenda.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.com.alura.agenda.database.converter.ConversorCalendar;
import br.com.alura.agenda.database.converter.ConversorTipoTelefone;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

@Database(entities = {Aluno.class, Telefone.class}, version = 6, exportSchema = false)
@TypeConverters({ConversorCalendar.class, ConversorTipoTelefone.class})
public abstract class AgendaDatabase extends RoomDatabase {

    private static final String NAME_DB = "agenda.db";
    private static AgendaDatabase instance;

    public abstract AlunoDAO getRoomAlunoDAO();
    public abstract TelefoneDAO getTelefoneDAO();

    public static AgendaDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context, AgendaDatabase.class, NAME_DB)
                    .allowMainThreadQueries()
                    // SÓ USAR ENQUANTO O APP NÃO ESTIVER EM PROD. DROPA TUDO E REFAZ O BANCO
                    //.fallbackToDestructiveMigration()
                    .addMigrations(AgendaMigrations.TODAS_MIGRATIONS)
                    .build();
        }

        return instance;
    }
}
