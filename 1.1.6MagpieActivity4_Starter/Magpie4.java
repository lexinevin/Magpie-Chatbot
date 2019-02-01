/**
 * A program to carry on conversations with a human user.

 *
 */
public class Magpie4
{

    public String getGreeting()
    {
        return "Hello, let's talk.";
    }

    public String getResponse(String statement)
    {
        String response = "";
        if (statement.length() == 0)
        {
            response = "Say something, please.";
        }

        else if (findKeyword(statement, "no") >= 0)
        {
            response = "Why so negative?";
        }
        
        else if (findKeyword(statement, "How are you") >= 0)
        {
            response = "I'm enjoying life.";
        }
        
        else if (findKeyword(statement, "How is your day") >= 0)
        {
            response = "I'm enjoying life.";
        }
        
        else if (findKeyword(statement, "Tell me about") >= 0)
        {
            response = "It's not the best.";
        }
        
        else if (findKeyword(statement, "mother") >= 0
        || findKeyword(statement, "father") >= 0
        || findKeyword(statement, "sister") >= 0
        || findKeyword(statement, "brother") >= 0)
        {
            response = "Tell me more about your family.";
        }
        
        else if (findKeyword(statement, "sunny") >= 0
        || findKeyword(statement, "rainy") >= 0
        || findKeyword(statement, "rain") >= 0
        || findKeyword(statement, "windy") >= 0)
        {
            response = "The weather is so fascinating to me.";
        }
        
        else if (findKeyword(statement, "travel") >= 0
        || findKeyword(statement, "airplane") >= 0
        || findKeyword(statement, "bus") >= 0
        || findKeyword(statement, "roadtrip") >= 0)
        {
            response = "I love to travel. Especially when I fly in airplanes.";
        }

        // Responses which require transformations
        else if (findKeyword(statement, "I want to", 0) >= 0)
        {
            response = transformIWantToStatement(statement);
        }

        else
        {
            // Look for a two word (you <something> me)
            // pattern
            int psn = findKeyword(statement, "you", 0);

            if (psn >= 0
            && findKeyword(statement, "me", psn) >= 0)
            {
                response = transformYouMeStatement(statement);
            }
            
            // New I,You section for hate
            else if (findKeyword(statement, "I",0) >= 0
            && findKeyword(statement, "you",statement.length()-4) >= 0) 
            {
                response = transformIYouStatement(statement);
            }
             
           
            else
            {
                response = getRandomResponse();
            }
        }
        return response;
    }
   
    // New I,You section below
   
    private String transformIYouStatement(String statement)
    {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement
                .length() - 1);
        }

        int psn = findKeyword (statement, "I", 0);
        int endPsn = findKeyword (statement, "you", statement.length()-4);
        String secondStatement = statement.substring(psn + 1).trim();
        String somethingStatement = statement.substring(1, endPsn);
        return "Why do you" + somethingStatement + "me?";
    }

    
    private String transformIWantToStatement(String statement)
    {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement
                .length() - 1);
        }
        int psn = findKeyword (statement, "I want to", 0);
        String restOfStatement = statement.substring(psn + 9).trim();
        return "What would it mean to " + restOfStatement + "?";
    }

    private String transformYouMeStatement(String statement)
    {
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement
                .length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement
                .length() - 1);
        }

        int psnOfYou = findKeyword (statement, "you", 0);
        int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);

        String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
        return "What makes you think that I " + restOfStatement + " you?";
    }

    private int findKeyword(String statement, String goal, int startPos)
    {
        String phrase = statement.trim();
        //  The only change to incorporate the startPos is in the line below
        int psn = phrase.toLowerCase().indexOf(goal.toLowerCase(), startPos);

        //  Refinement--make sure the goal isn't part of a word 
        while (psn >= 0) 
        {
            //  Find the string of length 1 before and after the word
            String before = " ", after = " "; 
            if (psn > 0)
            {
                before = phrase.substring (psn - 1, psn).toLowerCase();
            }
            if (psn + goal.length() < phrase.length())
            {
                after = phrase.substring(psn + goal.length(), psn + goal.length() + 1).toLowerCase();
            }

            //  If before and after aren't letters, we've found the word
            if (((before.compareTo ("a") < 0 ) || (before.compareTo("z") > 0))  //  before is not a letter
            && ((after.compareTo ("a") < 0 ) || (after.compareTo("z") > 0)))
            {
                return psn;
            }

            //  The last position didn't work, so let's find the next, if there is one.
            psn = phrase.indexOf(goal.toLowerCase(), psn + 1);

        }

        return -1;
    }

    private int findKeyword(String statement, String goal)
    {
        return findKeyword (statement, goal, 0);
    }


    private String getRandomResponse()
    {
        final int NUMBER_OF_RESPONSES = 4;
        double r = Math.random();
        int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
        String response = "";

        if (whichResponse == 0)
        {
            response = "Interesting, tell me more.";
        }
        else if (whichResponse == 1)
        {
            response = "Hmmm.";
        }
        else if (whichResponse == 2)
        {
            response = "Do you really think so?";
        }
        else if (whichResponse == 3)
        {
            response = "You don't say.";
        }

        return response;
    }

}
