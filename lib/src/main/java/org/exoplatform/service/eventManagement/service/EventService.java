/*
 * Copyright (C) 2014 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.exoplatform.service.eventManagement.service;

import org.exoplatform.service.eventManagement.entities.Event;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.core.ManageableRepository;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import java.util.ArrayList;
import java.util.List;


/**
 * @author <a href="mailto:foo@bar.org">Foo Bar</a>
 * @version $Id: Body Header.java 34027 2009-07-15 23:26:43Z aheritier $
 */
public class EventService
{
   private String eventRootNode = "EventManagement";
   private RepositoryService repositoryService;
   private static final Log LOG = ExoLogger.getLogger("eventManagement.service.EventService");

   public EventService(RepositoryService repositoryService)
   {
      this.repositoryService = repositoryService;
   }


   public Event addEvent(String pName, String desc)
   {
      return new Event();
   }

  /* public List<Event> getAllEvent()
   {

   }
   */


   public boolean removeEvent(int id)
   {
      return true;
   }

   protected Node getEventRootNode() throws RepositoryException
   {
      SessionProvider sessionProvider = null;
      Node node = null;
      try
      {
         sessionProvider = SessionProvider.createSystemProvider();
         ManageableRepository currentRepo = repositoryService.getCurrentRepository();
         Session session = sessionProvider.getSession(currentRepo.getConfiguration().getDefaultWorkspaceName(), currentRepo);
         Node rootNode = session.getRootNode();
         if (!rootNode.hasNode(eventRootNode))
         {
            node = rootNode.addNode(eventRootNode);
            rootNode.save();
         }
         else
         {
            node = rootNode.getNode(eventRootNode);
         }

      }
      catch (Exception e)
      {
         LOG.error(e);
      }
      return node;
   }

   public Node getEventNodeById(long id)
   {
      Node pRoot = null;
      try
      {
         pRoot = getEventRootNode();
         String statement = "SELECT * FROM exo:event WHERE jcr:path LIKE '" +
            pRoot.getPath() + "/%' AND idEvent='" + id + "'";

         Query query = pRoot.getSession().getWorkspace().getQueryManager().createQuery(statement, Query.SQL);
         for (NodeIterator iter = query.execute().getNodes(); iter.hasNext(); )
         {
            Node p = iter.nextNode();
            return p;
         }
      }
      catch (RepositoryException e)
      {
         LOG.error(e);
      }

      return null;
   }

}
