package com.gzeport.app.gps.dwr;

import java.util.HashMap;
import java.util.Map;

import org.directwebremoting.Container;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;

public class AddScriptSessionListener implements ScriptSessionListener {
 public static Map<String, ScriptSession> sc=new HashMap<String, ScriptSession>();
 
 public void sessionCreated(ScriptSessionEvent ev) {
  WebContext webContext = WebContextFactory.get(); 
  sc.put(webContext.getSession().getId(), ev.getSession());
  System.out.println("add --------->"+ev.getSession().getId());
 
 }

 public void sessionDestroyed(ScriptSessionEvent ev) {
  System.out.println("remove --------->"+ev.getSession().getId());
 }

}

