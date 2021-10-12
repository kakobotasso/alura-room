package br.com.alura.agenda.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.com.alura.agenda.database.converter.ConversorCalendar;
import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;

@Database(entities = {Aluno.class}, version = 4, exportSchema = false)
@TypeConverters({ConversorCalendar.class})
public abstract class AgendaDatabase extends RoomDatabase {

    private static final String NAME_DB = "agenda.db";
    private static AgendaDatabase instance;

    public abstract AlunoDAO getRoomAlunoDAO();

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
