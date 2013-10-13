/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.zisco.pandora.workflow;

/**
 *
 * @author zisco
 */
public class Task {

    public String title;

    public Task(String newTitle) {
        title = newTitle;
    }

    public String toString() {
        return new String(this.title);
    }
}
