# ECM2414 Card Game

This was a university coursework project I completed with a partner for the University of Exeter. We achieved a mark of 83% for this coursework, earning a high first-class classification.

## Specification

In this project, we developed a simulation for a fictional card game in Java. The main objective was to simulate all the players' hands simultaneously using multithreading techniques. 
This involved careful design and management of the shared memory and access to this memory. 

In our final design, each player would check a shared memory location to check if any other player had won. If another player had won, all the players would either stop playing or play until they have played at least as many moves at the winning player to see if they might win with fewer moves.
If a player wins, they will write to this shared memory to inform all the other players that they have won. 

This design ensures a small shared memory surface area and minimised the amount of writes which creates a more efficient program.

## Testing
We tested this project using Junit4. We used a variery of testing methods, including unit tests and larger scale more general tests. 

Every class and every method in these classes were heavily unit tested to ensure no changed we made we breaking changes. Additionally we wrote tests which checked the game was deterministic and achieved the same result every game.
The largest scale test we performed including 100 asynchronous players and produces the same result every time. 
