/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.app.visitor;

import org.app.map.ZooMap;

/**
 *
 * @author Tam
 */
public class VIPVisitor extends Visitor{
    public VIPVisitor(int x, int y, ZooMap map, int number, double money){
        super(x, y, map, number, money);
    }
    
    /*@Override
    public void run(){
        System.out.println("VIP Visitor " + getNumber() +" with $" + getMoney() +" is created...");
        try{
            for (int i=1; i< 100; i++){
                Thread.sleep(20*1000);
                spendMoney();
                
            }
        
        } catch (Exception e){
        }
    }*/
    
    @Override
    public boolean spendMoney() {
        if (getMoney()>20){
            setMoney(getMoney() - 20);
            //System.out.println("VIP Visitor " + getNumber() +" spent $20...Money is now " + getMoney());
            return true;
        }
        else return false;//System.out.println("VIP Visitor " + getNumber() +" doesnot have enough money to spend...Money is now " + getMoney());
     
    }
    
}
