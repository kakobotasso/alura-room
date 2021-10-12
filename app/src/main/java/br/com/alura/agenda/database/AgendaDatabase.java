package br.com.alura.agenda.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;

@Database(entities = {Aluno.class}, version = 3, exportSchema = false)
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
                    .addMigrations(new Migration(1, 2) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            database.execSQL("ALTER TABLE aluno ADD COLUMN sobrenome TEXT");
                        }
                    }, new Migration(2, 3) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            // Cria nova tabela
                            database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT, `telefone` TEXT, `email` TEXT)");
                            // Copia os dados da antiga para a nova
                            database.execSQL("INSERT INTO Aluno_novo (id, nome, telefone, email) SELECT id, nome, telefone, email FROM Aluno");
                            // Remove tabela antiga
                            database.execSQL("DROP TABLE Aluno");
                            // Renomeia a tabela nova
                            database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno");
                        }
                    })
                    .build();
        }

        return instance;
    }
}
