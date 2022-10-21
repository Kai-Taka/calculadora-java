package br.com.bb.Calc;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Solve {

    static Operacoes ope;
    static Scanner sc = new Scanner(System.in);
    int posBuffer;

    Solve(Operacoes ope)
    {
        Solve.ope = new Operacoes();   
    }

    /*

    public static void main(String[] args) {
        //Object that gets input from user
        //While getting input
        Boolean continuar = true;

        //Main question Loop
        while (continuar) 
        {
            //Await input from user
            String command = sc.nextLine();
            //Logging solution
            System.out.println("solution to: " + command);
            //Printing solution
            System.out.println(solve(command));
           
            //Wait for continue command
            System.out.println("pressione ENTER para o próximo comando");
            sc.nextLine();
        }
    }*/

    public String solve(String command)
    {
        //pos of last relevant operation
        int lastOp = 0;
        //List containing all relevant Brackets positions
        ArrayList<Integer> lastBrackets = new ArrayList<Integer>();
        //Initializing list with pos 0 bracket
        lastBrackets.add(0);
        //If bracket needs closing
        Boolean close = false;
        //If a opening bracket needs to be added
        Boolean addBracket = false;
        //Number of opening brackets to ignore
        int ignore = 0;
        //Solution buffer
        StringBuilder buffer = new StringBuilder("(");

        //Check if needs to use memory
        if 
        (
            command.charAt(0) == '+' ||
            command.charAt(0) == '*' ||
            command.charAt(0) == '/'
        )
        {
            command = Solve.ope.getMemory()[0].toString() + command;
        }
        //Ask if it is -1 or memory -1
        else if (command.charAt(0) == '-')
        {
            while (true)
            {
                System.out.println("Usar memória? (y/n)");
                String aws = sc.nextLine();
                if (aws.equals("y"))
                {
                    command = Solve.ope.getMemory()[0].toString() + command;
                    break;
                }
                else if (aws.equals("n"))
                {
                    break;
                }
            }
        }

        //BEGIN PROCESS LOOP
        for (int i = 0; i < command.length(); i++)
        {
            //Build buffer
            //Get current char C
            char c = command.charAt(i);

            //if not ignoring characters add character to working buffer
            if (ignore == 0)
            {
                buffer.append(c);
            }

            //Add necessary brackets
            //If not ignoring AND character is an operation
            switch (c)
            {
                case '+':
                case '-':
                case '*':
                case '/':
                    if (ignore == 0)
                    {
                        //If needs to close brackets and operation is low priorty
                        if (close && (c=='+' || c=='-'))
                        {
                            closeBrackets(buffer, lastBrackets, false);

                            close = false;
                        }

                        //If operation is high priority add reduntant brackets
                        if (c == '*' || c=='/')
                        {
                            //if there are low priority operations
                            if (addBracket)
                            {
                            buffer.insert(lastOp, '(');
                            lastBrackets.add(lastOp);
                            close = true;
                            addBracket = false;
                            }
                        }
                        //If there are low priority operations
                        else //if (!manualBracket)
                        {
                            addBracket=true;
                        }
                        //Update last important operation
                        lastOp = buffer.length();
                    }
                    break;

                //manual brackets    
                case '(':
                    if (ignore == 0)
                    {   //Remove character added at begining of recursion
                        buffer.deleteCharAt(buffer.length()-1);
                        //Begin recursion 
                        //Add recursion solution to buffer
                        buffer.append(solve(command.substring(i+1)));
                    }
                    //ignore next characters that have already been treated
                    ignore++;
                    break;
                
                //custom function
                case '[':
                    buffer.deleteCharAt(buffer.length()-1);
                    this.posBuffer = i;
                    customFunction(command, buffer);
                    i = this.posBuffer;
                    break;
                
                //if End of ignore cycle
                case ')':
                    // If ignoring
                    if (ignore > 0)
                    {
                        ignore-=1;
                    }
                    //If in recursion
                    else
                    {
                        //Remove character added by end of recursion
                        buffer.deleteCharAt(buffer.length()-1);
                        //Solve and add back bracket
                        closeBrackets(buffer, lastBrackets, true);
                        //Recursion end LOG
                        //System.out.println(buffer);
                        return buffer.toString();
                    }
                    break;
            }
            
            //FINISHED PROCESS LOOP
        }
        
        //If program needs closing an unfinished bracket
        if (close)
        {
            //Solve priority op
            closeBrackets(buffer, lastBrackets, true);
        }
        //Solve all low priority operations
        closeBrackets(buffer, lastBrackets, true);
        //Return solution main function

        return buffer.toString();
    }

    private String parseSum(String opes)
    {
        Boolean first = true;
        BigDecimal res = new BigDecimal(0);
        char opBuffer = '+';
        String buffer = "";

        for (char c: opes.toCharArray())
        {
            if (buffer.length() > 0 &&
            (
                c == '+' ||
                c == '-' ||
                c == '*' ||
                c == '/'
            ))
            {
                first = false;
                switch (opBuffer)
                {
                    case '+':
                        res = Solve.ope.adicao(res, new BigDecimal(Double.parseDouble(buffer)));
                        buffer = "";
                        opBuffer = c;
                        break;
                    case '-':
                        res = Solve.ope.sub(res ,new BigDecimal(Double.parseDouble(buffer)));
                        buffer = "";
                        opBuffer = c;
                        break;
                    case '*':
                        res = Solve.ope.mult(res, new BigDecimal(Double.parseDouble(buffer)));
                        buffer = "";
                        opBuffer = c;
                        break;
                    case '/':
                        res = Solve.ope.div(res, new BigDecimal(Double.parseDouble(buffer)));
                        buffer = "";
                        opBuffer = c;
                        break;
                    default:
                        if ((c >= 48 && c <= 57) || c=='.')
                        {
                            buffer += c;
                        }
                }

            }
            else
            {
                if ((c >= 48 && c <= 57) || c=='.')
                {
                    buffer += c;
                }
                else if 
                (
                    (
                        c == '+' ||
                        c == '-' ||
                        c == '*' ||
                        c == '/'
                    ) && 
                    first
                )
                {
                    opBuffer = c;
                }
            }
        }

        if (buffer.length() > 0)
        {
            switch (opBuffer)
                {
                    case '+':
                        res = Solve.ope.adicao(res, new BigDecimal(Double.parseDouble(buffer)));
                        break;
                    case '-':
                        res = Solve.ope.sub(res, new BigDecimal(Double.parseDouble(buffer)));
                        break;
                    case '*':
                        res = Solve.ope.mult(res, new BigDecimal(Double.parseDouble(buffer)));
                        break;
                    case '/':
                        res = Solve.ope.div(res, new BigDecimal(Double.parseDouble(buffer)));
                        break;
                }
        }

        return res.toString();
    }

    private void closeBrackets(StringBuilder buffer, ArrayList<Integer> lastBrackets, boolean finish)
    {
        /*
         *  The following expression
         * (finish ? 0 : 1) 
         * Reffers to the need of closing brackets 
         * of non-solverd high priority operations
         */
        buffer.insert(buffer.length() - (finish ? 0 : 1), ')');
        //Logging solve order logic to user
        System.out.println("senidng to solve " + buffer.substring(lastBrackets.get(lastBrackets.size()-1),
            buffer.length()-(finish ? 0 : 1)));

        //Replacing solved operation on buffer
        buffer.replace(lastBrackets.get(lastBrackets.size()-1),
            buffer.length()-(finish ? 0 : 1),
            parseSum(buffer.substring(lastBrackets.get(lastBrackets.size()-1), 
                buffer.length()-(finish ? 0 : 1))));
        
        //Popping solved brackets
        lastBrackets.remove(lastBrackets.size()-1);
    }

    void customFunction(String command, StringBuilder buffer)
    { 
        boolean stop = false;
        StringBuilder func = new StringBuilder("");
        for (int i = this.posBuffer; i<command.length(); i++)
        {
            char c = command.charAt(i);
            switch (c)
            {
                case ']':
                    stop=true;
                    break;

                default:
                    func.append(c);
            }

            if (stop)
            {
                this.posBuffer = i;
                break;
            }
        }
        
        Pattern pattern = Pattern.compile("([a-zA-Z]+)\\((.+)\\)");
        Matcher matcher = pattern.matcher(command);
        boolean matchFound = matcher.find();
        if (matchFound)
        {
            String function = matcher.group(1).toString();
            String[] args = matcher.group(2).toString().split(",");
            String result = solveFunction(function, args);
            if (result != null)
            {
                buffer.append(result);
            }
        }
        else
        {
            System.out.println("no function found");
        }
    }

    String solveFunction(String func, String[] args)
    {
        switch (func)
        {
            case "pow":
                BigDecimal base = new BigDecimal(Double.parseDouble(args[0]));
                int power = Integer.parseInt(args[1]);
                return ope.pow(base, power).toString();
            default:
                return null;
        }
        
    }

}

