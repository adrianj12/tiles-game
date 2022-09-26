### _Tiles Game_
This is a tile-matching game based on the [NY Times web game](https://www.nytimes.com/puzzles/tiles).

#### How to Play:
1. Choose a beginning tile
2. Select another tile that has a matching object/graphic
   1. Any elements that match on both tiles disappear 
   2. If you clear the tile, don't worry, your streak is still going! Just choose another starting tile.
3. Continue until all tiles are cleared
  - __Challenge:__ Try to clear as many tiles as you can in one streak (Hint: you can clear the whole board in 1 streak!)
---
#### Program Details:
- **Entry point class**: TilesGame.java
- No command line arugments
- **Unfinished features:**
  - Make it more obvious (alert box maybe) when all tiles are cleared
  - Make elements disappear in shrinking animation (like NYTimes version)
- **Known Bugs:**
  - Cannot handle more than 4 color choices for elements
  - Sometimes exception is thrown randomly when clicking tiles quickly -- still trying to figure out