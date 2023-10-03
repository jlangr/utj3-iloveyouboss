package iloveyouboss.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ATableAccess_WithMockedDependencies {
   static final String TABLE_NAME = "TestTableAccess";
   static final String ID_COLUMN = "id";

   @InjectMocks
   TableAccess table;

   @Mock
   DB db;
   @Mock
   java.sql.Connection connection;

   record TestTableAccess(int id, String x) {}

   @BeforeEach
   void createTableAccess() {
      DB.set(db);
      table = new TableAccess(TABLE_NAME, ID_COLUMN);
   }

   @AfterEach
   void resetDb() {
      DB.reset();
   }

   @Nested
   class Get {
      @Test
      void rethrowsWhenPrepareFails() throws SQLException {
         when(db.poolConnection()).thenReturn(connection);
         when(connection.prepareStatement(anyString()))
            .thenThrow(new SQLException("because"));

         Executable act = () -> table.get(99, results -> new TestTableAccess(1, ""));

         var thrown = assertThrows(RuntimeException.class, act);
         assertTrue(thrown.getMessage().contains("error retrieving from row in"));
      }
   }
}
