/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zisco.pandora.workflow;


import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.alg.CycleDetector;

/**
 *
 * @author zisco
 */
public class Workflow {
    DefaultDirectedGraph<Task, Edge> wf;
    CycleDetector<Task, Edge> cd;
    BreadthFirstIterator<Task, Edge> bi;

    public Workflow() {
        wf = new DefaultDirectedGraph<Task, Edge>(Edge.class);
        cd = new CycleDetector<Task, Edge>(wf);
        
    }

    public void addTask(Task newTask) {
        wf.addVertex(newTask);
    }

    public void addTask(Task newTask, Task parentTask)
    throws LoopException {
        if(wf.addVertex(newTask)) {
            Object e = wf.addEdge(parentTask, newTask);
            if (e == null) throw new LoopException();
        }
        if(cd.detectCycles()) throw new LoopException();
    }

   public ArrayList<Task> getTasksSequence() {
       ArrayList<Task> tasks = new ArrayList<Task>();
       bi = new BreadthFirstIterator<Task, Edge>(wf);
       while(bi.hasNext()) {
        Task t = (Task) bi.next();
        tasks.add(t);
       }
       return tasks;
   }

   public int getTasksCount() {
        return wf.vertexSet().size();
   }


public static void main(String[] args) {

    Workflow w = new Workflow();
        try {
            Task t1 = new Task("t1");
            Task t1bis = new Task("t1bis");
            //Task t2 = new Task("t2");
            Task t3 = new Task("t3");
            Task t4 = new Task("t4");
            Task t5 = new Task("t5");
            Task t6 = new Task("t6");
            Task t7 = new Task("t7");

            w.addTask(t1);
            w.addTask(t1bis);
            /*
            w.addTask(t2, t1);

            */
            w.addTask(t3, t1);
            w.addTask(t5, t1);
            w.addTask(t4, t3);
            w.addTask(t6, t1);
            w.addTask(t7, t3);
              

            System.out.println("eccomi");
            ArrayList<Task> a = w.getTasksSequence();

            System.out.println("size: " +a.size());
            System.out.println("size internal: " +w.getTasksCount());

            Iterator<Task> t = a.iterator();
            while(t.hasNext()) System.out.println((t.next()).toString());

        } catch (Exception ex) {
            Logger.getLogger(Workflow.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

}
