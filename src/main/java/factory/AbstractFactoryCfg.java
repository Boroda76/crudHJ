package factory;

import dao.UserDAO;
import factory.impl.AbstractFactoryImplHibernate;
import factory.impl.AbstractFactoryImplJDBC;
import util.PropertiesReader;

import java.util.Optional;
import java.util.Properties;

public class AbstractFactoryCfg{
    private static final Properties properties = PropertiesReader.readProperties("factory.properties");
    private final AbstractFactory factory;
    private static class AbstractFactoryHolder{
        private static final Optional<AbstractFactory> factory=new AbstractFactoryCfg().getFactory();
    }
    private AbstractFactoryCfg(){
        switch (properties.getProperty("version")) {
            case "jdbc":
               this.factory= new AbstractFactoryImplJDBC();
               break;
            case "hibernate":
                this.factory= new AbstractFactoryImplHibernate();
                break;
            default:
                this.factory= null;
        }
    }

    public Optional<AbstractFactory> getFactory() {
        Optional<AbstractFactory> optionalAbstractFactory=Optional.ofNullable(this.factory);
        return optionalAbstractFactory;
    }

    public static Optional<AbstractFactory> createFactory() {
        return AbstractFactoryHolder.factory;
    }

}
