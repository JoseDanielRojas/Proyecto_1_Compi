/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 *
 * @author Pablo
 */
public class SequentialCase extends Cases {

    public SequentialCase(Cases case1,Cases case2,SourcePosition thePosition) {
        super(thePosition);
        Cas1=case1;
        Cas2=case2;
    }

    @Override
    public Object visit(Visitor v, Object o) {
         return v.visitSequentialCase(this,o);
    }
    public Cases Cas1,Cas2;
}
