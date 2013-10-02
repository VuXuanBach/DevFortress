package org.app.visitor;

import org.app.map.ZooMap;

/**
 *
 * @author dell
 */
public class CasualVisitor extends Visitor{

    public CasualVisitor(int x, int y, ZooMap map, int number, double money){
        super(x, y, map, number, money);
    }
   
    /*@Override
    public void run() {
        System.out.println("Normal Visitor " + getNumber() +" with $" + getMoney() +" is created...");
        try{
            for (int i=1; i< 100; i++){
                Thread.sleep(30*1000);
                spendMoney();
                
            }
        
        } catch (Exception e){
        }
    }*/
    
    @Override
    public boolean spendMoney() {
        if (getMoney()>10){
            setMoney(getMoney() - 10);
            //System.out.println("Normal Visitor " + getNumber() +" spent $10...Money is now " + getMoney());
            return true;
        }
        else return false;//System.out.println("Normal Visitor " + getNumber() +" doesnot have enough money to spend...Money is now " + getMoney());
    }
    
    
    
}
