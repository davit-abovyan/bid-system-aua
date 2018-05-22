package main.java.aua.service;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

import aua.dao.ItemDAO;
import aua.model.Item;

public class ItemService {
    public void addItem(Item object) throws NamingException {
        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");

        //use HTTP upgrade, an initial upgrade requests is sent to upgrade to the remoting protocol
        jndiProperties.put(Context.PROVIDER_URL,"remote://localhost:8080");

        final Context context = new InitialContext(jndiProperties);
        ItemDAO dao = (ItemDAO) context.lookup("ejb:/server-side/ItemDAOJBC!aua.dao.ItemDAO");
        dao.add(object);
    }
}
