
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/**
 * 
 * @author ljs
 * 2011-05-24
 * 
 * 把中缀表达式转换成一棵二叉树，然后通过后序遍历计算表达式的值
 * 1. unary operator like -3  is not supported
 * 2. only support + - * / and digits 0-9
 *
 */
public class InfixExpression {
     
    public class Node{
        char c;
        Node left;
        Node right;
         
        public Node(char c){
            this.c = c;
        }
    }
     
    private String infixExpr;
    private Node binTree;
    public InfixExpression(String infixExpr){
        this.infixExpr = infixExpr;
    }
     
     
    public Node toBinaryTree() throws Exception{
        binTree = toBinaryTree(this.infixExpr);
        return binTree;
    }
     
    public double evalUsingPostfix(){
        List<Character> postfixSymbols = new ArrayList<Character>();
        postOrderTraverse(this.binTree,postfixSymbols);
         
        Stack<Double> stack = new Stack<Double>();
        for(char c:postfixSymbols){
            switch(c){
                case '+':{
                    double op2 = stack.pop();
                    double op1 = stack.pop();
                    double result = op1 + op2;
                    stack.push(result);
                    }
                    break;
                case '-':{
                    double op2 = stack.pop();
                    double op1 = stack.pop();
                    double result = op1 - op2;
                    stack.push(result);
                    }
                    break;
                case '*':{
                    double op2 = stack.pop();
                    double op1 = stack.pop();
                    double result = op1 * op2;
                    stack.push(result);
                    }
                    break;
                case '/':{
                    double op2 = stack.pop();
                    double op1 = stack.pop();
                    double result = op1 / op2;
                    stack.push(result);
                    }               
                    break;
                default:
                    double val = c-'0';
                    stack.push(val);
                    break;
            }
        }   
         
        return stack.pop();
    }
     
    private void postOrderTraverse(Node root,List<Character> postfixSymbols){
        if(root == null)
            return;
        //traverse using postorder
        postOrderTraverse(root.left,postfixSymbols);
     
        postOrderTraverse(root.right,postfixSymbols);
     
        postfixSymbols.add(root.c);             
    }
     
    private Node toBinaryTree(String subexpr) throws Exception{
        subexpr = subexpr.trim();
        int len = subexpr.length();
         
         
        boolean checkRight = false;
        Node rootNode = null;
        Node leftNode = null;
         
        int i = 0;
        Node tree=null;
        while(i<len) {
            char c = subexpr.charAt(i);
            if(c>='0' && c<='9'){
                i++;
                 
                if(checkRight){
                    checkRight = false;
                    if(rootNode == null)
                        throw new Exception("input is not a valid infix expr.");
                    rootNode.right = new Node(c);
                    //for expr. 2+5+3 when c='5'
                    leftNode = rootNode;
                }else{
                    leftNode = new Node(c);
                }               
            }else if(c=='('){
                Stack<Character> stack = new Stack<Character>();
                StringBuffer sb = new StringBuffer();
                int p = i;
                stack.push('(');
                while(p<len){
                    p++;
                    char symbol = subexpr.charAt(p);
                                                             
                    if(symbol=='('){                
                        sb.append(symbol);
                        stack.push(symbol);
                    }else if(symbol==')'){                      
                         
                        if(stack.isEmpty()){
                            throw new Exception("input is not a valid infix expr.");
                        }else{
                            stack.pop();
                        }
                        if(stack.isEmpty()) {
                            //the last symbol ) is removed                      
                            break;
                        }else{
                            sb.append(symbol);
                        }
                    }else{
                        sb.append(symbol);
                    }
                }
                if(!stack.isEmpty())
                    throw new Exception("input is not a valid infix expr.");
                String subtreeExpr=sb.toString();               
                Node subtree = toBinaryTree(subtreeExpr);   
                 
                i = ++p;
                 
                if(checkRight){
                    checkRight = false;
                    if(rootNode == null)
                        throw new Exception("input is not a valid infix expr.");
                     
                    rootNode.right = subtree;
                     
                    //for expr. 1+(2+5)+3 when subtree = (2+5)
                    leftNode = rootNode;
                }else{
                    leftNode = subtree;
                }               
                 
            }else if(c=='+' || c=='-' || c=='*' || c=='/'){
                i++;
                                 
                if(leftNode == null){
                    throw new Exception("input is not a valid infix expr.");
                }else{
                    Node node = new Node(c);            
                    node.left = leftNode;               
                    rootNode = node;
                    checkRight = true;          
                }                               
            }else if(c==' '){
                i++;
                //ignore blank char
            }else{
                throw new Exception("input is not a valid infix expr.");
            }           
        }
        if(i<len)
            throw new Exception("input is not a valid infix expr.");
         
        tree = leftNode;
        return tree;
    }
     
    public static void main(String[] args) throws Exception {
        String infixExpr ="8-((3+5)*(5-(6/2)))";
        //String infixExpr ="(2+2)*(2+2)";
        //String infixExpr ="((2 + 2 ) * 3 + 3 )/2";
        //String infixExpr ="(((2-6)+((7*2)*3))-5)";
        InfixExpression infix = new InfixExpression(infixExpr);
        Node binTree = infix.toBinaryTree();
             
        double result = infix.evalUsingPostfix();
        System.out.format("%s = %.2f%n",infixExpr,result);
    }
}