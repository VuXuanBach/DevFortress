/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.mediator;

/**
 *
 * @author BACH
 */
public interface Mediator {

    public void addColleague(Colleague colleague);

    public void send(String message, Colleague colleague);
}