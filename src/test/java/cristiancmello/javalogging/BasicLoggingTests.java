package cristiancmello.javalogging;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.logging.*;

public class BasicLoggingTests {
    @Test
    void loggingForDummies() {
        Logger.getGlobal().info("File created!"); // loga no logger global
        Logger.getGlobal().setLevel(Level.OFF); // desliga todo o logging
        Logger.getGlobal().info("Este log é suprimido");
    }

    @Test
    void loggingMoreProfessional() {
        // nome é associado ao nome package. Só associado, nada demais. Coloque o nome que quiser.
        final Logger loggerA = Logger.getLogger("com.package.one");

        loggerA.setLevel(Level.SEVERE);

        loggerA.info("Este é um log de warning que é jogado fora porque o nivel do logger é SEVERE");
        loggerA.severe("Este é um log severe que irá aparecer proque o nível do logger é SEVERE");

        final Logger loggerB = Logger.getLogger("com.package.two");

        loggerB.info("Este é um outro logger.");

        final Logger parentLogger = Logger.getLogger("org.parent");

        // Suponha um 'parentLogger' que só permite logar mensagens com nível WARNING
        parentLogger.setLevel(Level.WARNING);

        final Logger childLogger = Logger.getLogger("org.parent.childOne");

        childLogger.info("childLogger logou. Esta mensagem não irá aparecer porque é parte de parent (restringe para WARNING)");
        childLogger.warning("Esta mensagem de warning irá aparecer porque childLogger é parte de parent (restringe para WARNING)");
    }

    @Test
    void loggingAccuratedCaseVmOptim() {
        // Alguns casos otimizações da JVM dificultam o rastreio da origem de um log.
        // O log padrão registra o nome da classe e o método que o invocou. Mas nem sempre essa informação pode estar
        // intacta devido a níveis de otimização de execução da JVM
        final var mylogger = Logger.getLogger("myLoggerOne");

        mylogger.logp(
            Level.INFO,
            this.getClass().getName(),
            "loggingAccuratedCaseVmOptim",
            "Mensagem com bom rastreio mesmo após otimização."
        );

        mylogger.logp(
            Level.WARNING,
            "OutraClasseNaoSeiDeOnde",
            "OutroMetodoNaoSeiDeOnde",
            "Um log de warning que será consistente mesmo após otimização."
        );
    }

    /**
     * Não entendi muito bem a aplicabilidade e o conceito do entering e exiting.
     *
     * @throws IOException
     */
    @Test
    void convenientLoggingWithTracingMethods() throws IOException {
        final var myLogger = Logger.getLogger("myLoggerTwo");

        var handler = new FileHandler("logs.txt");
        handler.setFormatter(new SimpleFormatter());

        myLogger.addHandler(handler);

        myLogger.setLevel(Level.FINER);

        myLogger.entering(this.getClass().getName(), "convenientLoggingWithTracingMethods", "Java!");
        myLogger.exiting(this.getClass().getName(), "convenientLoggingWithTracingMethods");
    }

    /**
     * Qual aplicabilidade deste tipo de log?
     * */
    @Test
    void loggingUnexpectedExceptions() throws IOException {
        final var myLogger = Logger.getLogger("myLoggerThree");

        var handler = new FileHandler("logs.txt");
        handler.setFormatter(new SimpleFormatter());

        myLogger.addHandler(handler);

        myLogger.setLevel(Level.FINER);

        myLogger.throwing(this.getClass().getName(), "loggingUnexpectedExceptions", new IOException());
    }

    @Test
    void customizingLogFileProperties() throws IOException {
        // No arquivo mylogging.properties, a linha "myLoggerThree.level = SEVERE" indica que o Logger 'myLoggerThree'
        // só coleta logs com nível SEVERE. Ou seja, podemos customizar através de um arquivo esse comportamento.
        final var myLogger = Logger.getLogger("myLoggerThree");

        myLogger.info("Esta mensagem não será coletada porque não é SEVERE");
        myLogger.severe("Este mensagem será coletada porque é SEVERE");

        // Lendo logger config file 'otherlogging.properties'
        // ATENÇÃO: Loggers não enviam mensagem diretamente para um Console ou Arquivo. Quem faz isso é o HANDLER.
        // Se deseja que log seja postado num Console, defina a propriedade 'handlers= java.util.logging.ConsoleHandler'

        // Podemos mudar a partir deste ponto do código o logger config file usado. Precisa reiniciar o logmanager.
        System.setProperty("java.util.logging.config.file", "./src/main/resources/otherlogging.properties");
        LogManager.getLogManager().readConfiguration(); // em seguida, reinicie o logmanager

        final var myOtherLogger = Logger.getLogger("myotherlogging");
        myOtherLogger.info("Esta mensagem não será coletada porque não é WARNING (especificado em otherlogging.properties)");
        myOtherLogger.warning("Esta mensagem será coletada...");
    }

    @Test
    void updateLoggingConfiguration() {
        // TODO: pag 612 e 613 tem o exemplo do método 'LogManager.getLogManager().updateConfiguration'
    }
}
