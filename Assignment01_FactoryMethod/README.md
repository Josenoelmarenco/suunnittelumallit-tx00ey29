# Factory Method — RPG Map Generator

Solution for the *Factory Method* design-pattern assignment.

The program generates a random rectangular map for a role-playing game. The
concrete map type decides which terrain tiles populate the grid, using the
**Factory Method** pattern.

## Design

| Role in the pattern | Class |
|---|---|
| Product (abstract)  | `Tile` |
| Concrete products   | `SwampTile`, `WaterTile`, `RoadTile`, `ForestTile`, `BuildingTile` |
| Creator (abstract)  | `Map` — declares the factory method `createTile()` and `display()` |
| Concrete creators   | `CityMap` (road, forest, building), `WildernessMap` (swamp, water, forest) |
| Client              | `Game` — `main()` + factory method `createMap()` |

`Map.display()` is completely agnostic of the concrete tile types: it only
asks each tile for `getCharacter()`. Adding a new terrain type requires no
change to `Map`.

Tile characters: `S` swamp, `W` water, `R` road, `F` forest, `B` building.

## Build & run

```bash
javac *.java
java Game
```
