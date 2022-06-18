package Triangle.Writer;

import Triangle.AbstractSyntaxTrees.*;

import java.io.FileWriter;
import java.io.IOException;

// Agregado por Miguel Mesen
public class XmlWriterVisitor implements Visitor {

    private FileWriter fileWriter;

    XmlWriterVisitor(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    private void write(String line) {
        try {
            fileWriter.write(line);
            fileWriter.write('\n');
        } catch (IOException e) {
            System.err.println("Error while writing file for print the AST");
            e.printStackTrace();
        }
    }

    private String characterEntityReference(String character){
        if (character.compareTo("<") == 0)
            return "&lt;";
        else if (character.compareTo("<=") == 0)
            return "&lt;=";
        else
            return character;
    }

    @Override
    public Object visitAssignCommand(AssignCommand ast, Object o) {
        write("<AssignCommand>");
        ast.V.visit(this, null);
        ast.E.visit(this, null);
        write("</AssignCommand>");
        return null;
    }

    @Override
    public Object visitCallCommand(CallCommand ast, Object o) {
        write("<CallCommand>");
        ast.I.visit(this, null);
        ast.APS.visit(this, null);
        write("</CallCommand>");
        return null;
    }

    @Override
    public Object visitEmptyCommand(EmptyCommand ast, Object o) {
        write("<EmptyCommand/>");
        return null;
    }

    @Override
    public Object visitIfCommand(IfCommand ast, Object o) {
        write("<IfCommand>");
        ast.E.visit(this, null);
        ast.C1.visit(this, null);
        ast.C2.visit(this, null);
        write("</IfCommand>");
        return null;
    }

    @Override
    public Object visitElsifCommand(ElsifCommand ast, Object o) {
        write("<ElisifCommand>");
        ast.E.visit(this,null);
        ast.C.visit(this,null);
        write("</ElisifCommand>");
        return null;
    }

    @Override
    public Object visitLetCommand(LetCommand ast, Object o) {
        write("<LetCommand>");
        ast.D.visit(this, null);
        ast.C.visit(this, null);
        write("</LetCommand>");
        return null;
    }

    @Override
    public Object visitSequentialCommand(SequentialCommand ast, Object o) {
        write("<SequentialCommand>");
        ast.C1.visit(this, null);
        ast.C2.visit(this, null);
        write("</SequentialCommand>");
        return null;
    }

    @Override
    public Object visitWhileCommand(WhileCommand ast, Object o) {
        write("<WhileCommand>");
        ast.E.visit(this, null);
        ast.C.visit(this, null);
        write("</WhileCommand>");
        return null;
    }

    @Override
    public Object visitArrayExpression(ArrayExpression ast, Object o) {
        write("<ArrayExpression>");
        ast.AA.visit(this, null);
        write("</ArrayExpression>");
        return null;
    }

    @Override
    public Object visitBinaryExpression(BinaryExpression ast, Object o) {
        write("<BinaryExpression>");
        ast.E1.visit(this, null);
        ast.O.visit(this, null);
        ast.E2.visit(this, null);
        write("</BinaryExpression>");
        return null;
    }

    @Override
    public Object visitCallExpression(CallExpression ast, Object o) {
        write("<CallExpression>");
        ast.I.visit(this, null);
        ast.APS.visit(this, null);
        write("<CallExpression>");
        return null;
    }

    @Override
    public Object visitCharacterExpression(CharacterExpression ast, Object o) {
        write("<CharacterExpression>");
        ast.CL.visit(this, null);
        write("<CharacterExpression>");
        return null;
    }

    @Override
    public Object visitEmptyExpression(EmptyExpression ast, Object o) {
        write("<EmptyExpression/>");
        return null;
    }

    @Override
    public Object visitIfExpression(IfExpression ast, Object o) {
        write("<IfExpression>");
        ast.E1.visit(this, null);
        ast.E2.visit(this, null);
        ast.E3.visit(this, null);
        write("</IfExpression>");
        return null;
    }

    @Override
    public Object visitIntegerExpression(IntegerExpression ast, Object o) {
        write("<IntegerExpression>");
        ast.IL.visit(this, null);
        write("</IntegerExpression>");
        return null;
    }

    @Override
    public Object visitLetExpression(LetExpression ast, Object o) {
        write("<LetExpression>");
        ast.D.visit(this, null);
        ast.E.visit(this, null);
        write("</LetExpression>");
        return null;
    }

    @Override
    public Object visitRecordExpression(RecordExpression ast, Object o) {
        write("<RecordExpression>");
        ast.RA.visit(this, null);
        write("</RecordExpression>");
        return null;
    }

    @Override
    public Object visitUnaryExpression(UnaryExpression ast, Object o) {
        write("<UnaryExpression>");
        ast.O.visit(this, null);
        ast.E.visit(this, null);
        write("</UnaryExpression>");
        return null;
    }

    @Override
    public Object visitVnameExpression(VnameExpression ast, Object o) {
        write("<VnameExpression>");
        ast.V.visit(this, null);
        write("</VnameExpression>");
        return null;
    }

    @Override
    public Object visitBinaryOperatorDeclaration(BinaryOperatorDeclaration ast, Object o) {
        write("<BinaryOperatorDeclaration>");
        ast.O.visit(this, null);
        ast.ARG1.visit(this, null);
        ast.ARG2.visit(this, null);
        ast.RES.visit(this, null);
        write("</BinaryOperatorDeclaration>");
        return null;
    }

    @Override
    public Object visitConstDeclaration(ConstDeclaration ast, Object o) {
        write("<ConstDeclaration>");
        ast.I.visit(this, null);
        ast.E.visit(this, null);
        write("</ConstDeclaration>");
        return null;
    }

    @Override
    public Object visitFuncDeclaration(FuncDeclaration ast, Object o) {
        write("<FuncDeclaration>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        ast.T.visit(this, null);
        ast.E.visit(this, null);
        write("</FuncDeclaration>");
        return null;
    }

    @Override
    public Object visitProcDeclaration(ProcDeclaration ast, Object o) {
        write("<ProcDeclaration>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        ast.C.visit(this, null);
        write("</ProcDeclaration>");
        return null;
    }

    @Override
    public Object visitSequentialDeclaration(SequentialDeclaration ast, Object o) {
        write("<SequentialDeclaration>");
        ast.D1.visit(this, null);
        ast.D2.visit(this, null);
        write("</SequentialDeclaration>");
        return null;
    }

    @Override
    public Object visitTypeDeclaration(TypeDeclaration ast, Object o) {
        write("<TypeDeclaration>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        write("</TypeDeclaration>");
        return null;
    }

    @Override
    public Object visitUnaryOperatorDeclaration(UnaryOperatorDeclaration ast, Object o) {
        write("<UnaryOperatorDeclaration>");
        ast.O.visit(this, null);
        ast.ARG.visit(this, null);
        ast.RES.visit(this, null);
        write("</UnaryOperatorDeclaration>");
        return null;
    }

    @Override
    public Object visitVarDeclaration(VarDeclaration ast, Object o) {
        write("<VarDeclaration>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        write("</VarDeclaration>");
        return null;
    }

    @Override
    public Object visitVarFormalDeclaration(VarFormalDeclaration ast, Object o) {
        write("<VarFormaltDeclaration>");
        ast.E.visit(this, null);
        write("</VarFormaltDeclaration>");
        return null;
    }

    @Override
    public Object visitArrayDeclarationOF(ArrayDeclarationOF ast, Object o) {
        write("<ArrayDeclarationOF>");
        ast.E.visit(this, null);
        write("</ArrayDeclarationOF>");
        return null;
    }

    @Override
    public Object visitArrayDeclarationDOBLEDOT(ArrayDeclarationDOBLEDOT ast, Object o) {
        write("<ArrayDeclarationDOBLEDOT>");
        ast.E.visit(this, null);
        ast.C.visit(this, null);
        write("</ArrayDeclarationDOBLEDOT>");
        return null;
    }

    @Override
    public Object visitMultipleArrayAggregate(MultipleArrayAggregate ast, Object o) {
        write("<MultipleArrayAggregate>");
        ast.E.visit(this, null);
        ast.AA.visit(this, null);
        write("</MultipleArrayAggregate>");
        return null;
    }

    @Override
    public Object visitSingleArrayAggregate(SingleArrayAggregate ast, Object o) {
        write("<SingleArrayAggregate>");
        ast.E.visit(this, null);
        write("</SingleArrayAggregate>");
        return null;
    }

    @Override
    public Object visitVarValueDeclaration(VarValueDeclaration ast, Object o) {
        write("<VarValueDeclaration>");
        ast.E.visit(this, null);
        write("</VarValueDeclaration>");
        return null;
    }

    @Override
    public Object visitMultipleRecordAggregate(MultipleRecordAggregate ast, Object o) {
        write("<MultipleRecordAggregate>");
        ast.I.visit(this, null);
        ast.E.visit(this, null);
        ast.RA.visit(this, null);
        write("</MultipleRecordAggregate>");
        return null;
    }

    @Override
    public Object visitSingleRecordAggregate(SingleRecordAggregate ast, Object o) {
        write("<SingleRecordAggregate>");
        ast.I.visit(this, null);
        ast.E.visit(this, null);
        write("</SingleRecordAggregate>");
        return null;
    }

    @Override
    public Object visitConstFormalParameter(ConstFormalParameter ast, Object o) {
        write("<ConstFormalParameter>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        write("</ConstFormalParameter>");
        return null;
    }

    @Override
    public Object visitFuncFormalParameter(FuncFormalParameter ast, Object o) {
        write("<FuncFormalParameter>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        ast.T.visit(this, null);
        write("</FuncFormalParameter>");
        return null;
    }

    @Override
    public Object visitProcFormalParameter(ProcFormalParameter ast, Object o) {
        write("<ProcFormalParameter>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        write("</ProcFormalParameter>");
        return null;
    }

    @Override
    public Object visitVarFormalParameter(VarFormalParameter ast, Object o) {
        write("<VarFormalParameter>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        write("</VarFormalParameter>");
        return null;
    }

    @Override
    public Object visitEmptyFormalParameterSequence(EmptyFormalParameterSequence ast, Object o) {
        write("<EmptyFormalParameterSequence/>");
        return null;
    }

    @Override
    public Object visitMultipleFormalParameterSequence(MultipleFormalParameterSequence ast, Object o) {
        write("<MultipleFormalParameterSequence>");
        ast.FP.visit(this, null);
        ast.FPS.visit(this, null);
        write("</MultipleFormalParameterSequence>");
        return null;
    }

    @Override
    public Object visitSingleFormalParameterSequence(SingleFormalParameterSequence ast, Object o) {
        write("<SingleFormalParameterSequence>");
        ast.FP.visit(this, null);
        write("</SingleFormalParameterSequence>");
        return null;
    }

    @Override
    public Object visitConstActualParameter(ConstActualParameter ast, Object o) {
        write("<ConstActualParameter>");
        ast.E.visit(this, null);
        write("</ConstActualParameter>");
        return null;
    }

    @Override
    public Object visitFuncActualParameter(FuncActualParameter ast, Object o) {
        write("<FuncActualParameter>");
        ast.I.visit(this, null);
        write("</FuncActualParameter>");
        return null;
    }

    @Override
    public Object visitProcActualParameter(ProcActualParameter ast, Object o) {
        write("<ProcActualParameter>");
        ast.I.visit(this, null);
        write("</ProcActualParameter>");
        return null;
    }

    @Override
    public Object visitVarActualParameter(VarActualParameter ast, Object o) {
        write("<VarActualParameter>");
        ast.V.visit(this, null);
        write("</VarActualParameter>");
        return null;
    }

    @Override
    public Object visitEmptyActualParameterSequence(EmptyActualParameterSequence ast, Object o) {
        write("</EmptyActualParameterSequence>");
        return null;
    }

    @Override
    public Object visitMultipleActualParameterSequence(MultipleActualParameterSequence ast, Object o) {
        write("<MultipleActualParameterSequence>");
        ast.AP.visit(this, null);
        ast.APS.visit(this, null);
        write("</MultipleActualParameterSequence>");
        return null;
    }

    @Override
    public Object visitSingleActualParameterSequence(SingleActualParameterSequence ast, Object o) {
        write("<SingleActualParameterSequence>");
        ast.AP.visit(this, null);
        write("</SingleActualParameterSequence>");
        return null;
    }

    @Override
    public Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object o) {
        write("<AnyTypeDenoter/>");
        return null;
    }

    @Override
    public Object visitArrayTypeDenoter(ArrayTypeDenoter ast, Object o) {
        write("<ArrayTypeDenoter>");
        ast.IL.visit(this, null);
        ast.T.visit(this, null);
        write("</ArrayTypeDenoter>");
        return null;
    }

    @Override
    public Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object o) {
        write("<BoolTypeDenoter/>");
        return null;
    }

    @Override
    public Object visitCharTypeDenoter(CharTypeDenoter ast, Object o) {
        write("<CharTypeDenoter/>");
        return null;
    }

    @Override
    public Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object o) {
        write("<ErrorTypeDenoter/>");
        return null;
    }

    @Override
    public Object visitSimpleTypeDenoter(SimpleTypeDenoter ast, Object o) {
        write("<SimpleTypeDenoter>");
        ast.I.visit(this, null);
        write("</SimpleTypeDenoter>");
        return null;
    }

    @Override
    public Object visitIntTypeDenoter(IntTypeDenoter ast, Object o) {
        write("<IntTypeDenoter/>");
        return null;
    }

    @Override
    public Object visitRecordTypeDenoter(RecordTypeDenoter ast, Object o) {
        write("<RecordTypeDenoter>");
        ast.FT.visit(this, null);
        write("</RecordTypeDenoter>");
        return null;
    }

    @Override
    public Object visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast, Object o) {
        write("<MultipleFieldTypeDenoter>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        ast.FT.visit(this, null);
        write("</MultipleFieldTypeDenoter>");
        return null;
    }

    @Override
    public Object visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast, Object o) {
        write("<SingleFieldTypeDenoter>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        write("</SingleFieldTypeDenoter>");
        return null;
    }

    @Override
    public Object visitCharacterLiteral(CharacterLiteral ast, Object o) {
        write("<CharacterLiteral value=\"" + ast.spelling + "\"/>");
        return null;
    }

    @Override
    public Object visitIdentifier(Identifier ast, Object o) {
        write("<Identifier value=\"" + ast.spelling + "\"/>");
        return null;
    }

    @Override
    public Object visitIntegerLiteral(IntegerLiteral ast, Object o) {
        write("<IntegerLiteral value=\"" + ast.spelling + "\"/>");
        return null;
    }

    @Override
    public Object visitOperator(Operator ast, Object o) {
        write("<Operator value=\"" + characterEntityReference(ast.spelling) + "\"/>");
        return null;
    }

    @Override
    public Object visitDotVname(DotVname ast, Object o) {
        write("<DotVname>");
        ast.V.visit(this, null);
        ast.I.visit(this, null);
        write("</DotVname>");
        return null;
    }

    @Override
    public Object visitSimpleVname(SimpleVname ast, Object o) {
        write("<SimpleVname>");
        ast.I.visit(this, null);
        write("</SimpleVname>");
        return null;
    }

    @Override
    public Object visitSubscriptVname(SubscriptVname ast, Object o) {
        write("<SubscriptVname>");
        ast.V.visit(this, null);
        ast.E.visit(this, null);
        write("</SubscriptVname>");
        return null;
    }

    @Override
    public Object visitProgram(Program ast, Object o) {
        write("<Program>");
        ast.C.visit(this, null);
        write("</Program>");
        return null;
    }

    @Override
    public Object visitForDoCommand(ForDoCommand aThis, Object o) {
        write("<ForDoCommand>");
        aThis.VarDe.visit(this,null);
        aThis.E.visit(this,null);
        aThis.C1.visit(this,null);
        aThis.C2.visit(this,null);
        write("</ForDoCommand>");
        return null;
    }

    @Override
    public Object visitForUntilCommand(ForUntilCommand aThis, Object o) {
        write("<ForUntilCommand>");
        aThis.VarDe.visit(this,null);
        aThis.E.visit(this,null);
        aThis.U.visit(this,null);
        write("</ForUntilCommand>");
        return null;
    }

    @Override
    public Object visitForWhileCommand(ForWhileCommand aThis, Object o) {
        write("<ForWhileCommand>");
        aThis.VarDe.visit(this,null);
        aThis.E.visit(this,null);
        aThis.W.visit(this,null);
        write("</ForWhileCommand>");
        return null;
    }

    @Override
    public Object visitRepeatDoUntilCommand(RepeatDoUntilCommand aThis, Object o) {
        write("<RepeatDoUntilCommand>");
        aThis.C1.visit(this,null);
        aThis.E.visit(this,null);
        aThis.C2.visit(this,null);
        write("</RepeatDoUntilCommand>");
        return null;
    }

    @Override
    public Object visitRepeatDoWhileCommand(RepeatDoWhileCommand aThis, Object o) {
        write("<RepeatDoWhileCommand>");
        aThis.C1.visit(this,null);
        aThis.E.visit(this,null);
        aThis.C2.visit(this,null);
        write("</RepeatDoWhileCommand>");
        return null;
    }

    @Override
    public Object visitRepeatUntilCommand(RepeatUntilCommand aThis, Object o) {
        write("<RepeatUntilCommand>");
        aThis.C1.visit(this,null);
        aThis.E.visit(this,null);
        aThis.C2.visit(this,null);
        write("</RepeatUntilCommand>");
        return null;
    }

    @Override
    public Object visitRepeatWhileCommand(RepeatWhileCommand aThis, Object o) {
        write("<RepeatWhileCommand>");
        aThis.C1.visit(this,null);
        aThis.E.visit(this,null);
        aThis.C2.visit(this,null);
        write("</RepeatWhileCommand>");
        return null;
    }

    @Override
    public Object visitForVarDecl(ForVarDecl aThis, Object o) {
        write("<ForVarDecl>");
        aThis.I.visit(this,null);
        aThis.E.visit(this,null);
        write("</ForVarDecl>");
        return null;
    }

    @Override
    public Object visitWhenCase(WhenCase aThis, Object o) {
        write("<WhenCase>");
        aThis.CaseL.visit(this,null);
        aThis.CaseL2.visit(this,null);
        aThis.C.visit(this,null);
        write("</WhenCase>");
        return null;
    }

    @Override
    public Object visitChooseCommand(ChooseCommand aThis, Object o) {
        write("<ChooseCommand>");
        aThis.E.visit(this,null);
        aThis.Cas.visit(this,null);
        aThis.C.visit(this,null);
        write("</ChooseCommand>");
        return null;
    }

    @Override
    public Object visitIntegerCase(IntegerCase aThis, Object o) {
        write("<IntegerCase>");
        aThis.IL.visit(this,null);
        write("</IntegerCase >");
        return null;
    }

    @Override
    public Object visitCharacterCase(CharacterCase aThis, Object o) {
        write("<CharacterCase>");
        aThis.CL.visit(this,null);
        write("</CharacterCase>");
        return null;
    }

    @Override
    public Object visitEmptyCase(EmptyCase aThis, Object o) {
        write("<EmptyCase/>");
        return null;
    }

    @Override
    public Object visitPrivateDeclaration(PrivateDeclaration aThis, Object o) {
        write("<PrivateDeclaration>");
        aThis.D1.visit(this,null);
        aThis.D2.visit(this,null);
        write("</PrivateDeclaration>");
        return null;
    }

    @Override
    public Object visitSequentialCase(SequentialCase aThis, Object o) {
        write("<SequentialCase>");
        aThis.Cas1.visit(this,null);
        aThis.Cas2.visit(this,null);
        write("</SequentialCase>");
        return null;
    }

    @Override
    public Object visitReProcDeclaration(ReProcDeclaration aThis, Object o) {
        write("<ReProcDeclaration>");
        aThis.I.visit(this,null);
        aThis.FPS.visit(this,null);
        aThis.C.visit(this,null);
        write("</ReProcDeclaration>");
        return null; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitRecursiveProcFunc(RecursiveProcFunc aThis, Object o) {
        write("<RecursiveProcFunc>");
        aThis.RecPrFun.visit(this,null);
        write("</RecursiveProcFunc>");
        return null; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitReFuncDeclaration(ReFuncDeclaration aThis, Object o) {
        //To change body of generated methods, choose Tools | Templates.
        write("<ReProcFunction>");
        aThis.I.visit(this,null);
        aThis.FPS.visit(this,null);
        aThis.T.visit(this,null);
        aThis.E.visit(this,null);
        write("</ReProcFucnticon>");
        return null;
    }

    @Override
    public Object visitForWhileExtraCommand(ForWhileExtraCommand aThis, Object o) {
         write("<ForWhileCommand>");
        aThis.C1.visit(this,null);
        aThis.E.visit(this,null);
        aThis.C2.visit(this,null);
        write("</ForWhileCommand>"); 
        return null;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitForUntilExtraCommand(ForUntilExtraCommand aThis, Object o) {
        write("<ForUntilCommand>");
        aThis.C1.visit(this,null);
        aThis.E.visit(this,null);
        aThis.C2.visit(this,null);
        write("</ForUntilCommand>"); 
        return null;//To change body of generated methods, choose Tools | Templates.
    }

   



    

   
}
// -------------------------
