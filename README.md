# Smart-Calculator

**Calculator that not only adds, subtracts, and multiplies, but is also smart enough to remember variables and check your typo.**

### What your can do:
**1. A simple calculation:<br>**
  - 2 + 2 * 2

**2. Calculator also understand such commands like:<br>**
  - 1 +++ 2 * 3 -- 4<br>
Will output: 11

**3. Support variables:<br>**
 - a  =  3<br>
 - b= 4<br>
 - c =5<br>
 - a + b - c<br>
Will output: 2<br>

***You can reassign variable if you want:<br>***
 - a = 800<br>
 - a + b + c<br>
Now, output will be: 809<br>

**4. Supports parentheses and doing fine with priority:<br>**
 - 3 + 8 * ((4 + 3) * 2 + 1) - 6 / (2 + 1)<br>
Will output: 121<br>

**5. And of course supports very big number:<br>**
 - 112234567890 + 112234567890 * (10000000999 - 999)<br>
Output: 1122345679012234567890<br>

#### Some rules:<br>
You can't do something like: n = **a2a** | a = 7 **= 8** | 2 *** 5 | 6 **///** 2 | **(2+3** * 4 | 1 + **1)** * 18");<br>
If you try, you just get a communicate like: "Invalid assignment" or "Invalid expression", etc.

Type ***"/help"*** if you need help inside the calculator.

