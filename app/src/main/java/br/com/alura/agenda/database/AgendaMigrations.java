package br.com.alura.agenda.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class AgendaMigrations {
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Aluno ADD COLUMN sobrenome TEXT");
        }
    };
    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
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
    };
    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Aluno ADD COLUMN momentoDeCadastro INTEGER");
        }
    };

    private static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Aluno_novo` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome` TEXT, `telefoneFixo` TEXT, `telefoneCelular` TEXT, `email` TEXT, `momentoDeCadastro` INTEGER)");
            database.execSQL("INSERT INTO Aluno_novo (id, nome, telefoneFixo, email, momentoDeCadastro) SELECT id, nome, telefone, email, momentoDeCadastro FROM Aluno");
            database.execSQL("DROP TABLE Aluno");
            database.execSQL("ALTER TABLE Aluno_novo RENAME TO Aluno");
        }
    };

    public static final Migration[] TODAS_MIGRATIONS = { MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5};
}
