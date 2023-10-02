## Code Review

Reviewed by: Yanbai Jin, u7706930

Reviewing code written by: Heng Sun u7611510

Component: https://gitlab.cecs.anu.edu.au/u7611510/comp1110-ass2/-/blob/main/src/comp1110/ass2/Rug.java#L29-40
https://gitlab.cecs.anu.edu.au/u7611510/comp1110-ass2/-/blob/main/src/comp1110/ass2/Rug.java#L62-79

### Comments 

#### Best Features:
Using if statements to identify invalid rugIDs solves the potential problem that the constructor may cause rugIDs to be incoherent. I think his method structure is appropriate and effective.

#### Documentation:
The variables in the methods are reasonably named, and complex code parts also have corresponding comments to assist reading, the code maintains a consistent style throughout, with reasonable indentation and formatting.

#### Program Decomposition:
The code is composed of the constructor and two methods. The code is reasonable and performs specific tasks, such as parsing rugString, formatting strings, and getting an abbreviated rugString.

