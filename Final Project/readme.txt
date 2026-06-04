===================================== Q1 =======================================

Binary Search Tree:
time java WordReplacer warandpeace.txt warandpeace_replacements.txt bst > /dev/null

1st Run: 1.75s user 0.06s system 106% cpu 1.695 total
2nd Run: 1.67s user 0.05s system 110% cpu 1.547 total
3rd Run: 1.64s user 0.06s system 109% cpu 1.543 total
4th Run: 1.65s user 0.06s system 109% cpu 1.554 total
5th Run: 1.68s user 0.06s system 109% cpu 1.580 total

Average of Total: 1.5838s

Red-Black Tree:
time java WordReplacer warandpeace.txt warandpeace_replacements.txt rbt > /dev/null

1st Run: 0.74s user 0.06s system 122% cpu 0.648 total
2nd Run: 0.73s user 0.06s system 125% cpu 0.627 total
3rd Run: 0.73s user 0.04s system 145% cpu 0.533 total
4th Run: 0.73s user 0.06s system 124% cpu 0.636 total
5th Run: 0.73s user 0.05s system 128% cpu 0.611 total

Average of Total: 0.611s (611ms)

Hash Maps:
time java WordReplacer warandpeace.txt warandpeace_replacements.txt hash > /dev/null

1st Run: 0.50s user 0.05s system 137% cpu 0.395 total
2nd Run: 0.47s user 0.05s system 142% cpu 0.367 total
3rd Run: 0.48s user 0.04s system 173% cpu 0.300 total
4th Run: 0.47s user 0.06s system 142% cpu 0.368 total
5th Run: 0.47s user 0.05s system 167% cpu 0.307 total

Average of Total: 0.3474s (347.4ms)

===================================== Q2 =======================================

1. Expected Performance: 
	Fastest: Hash Maps, average-case Theta(1) time complexity for lookups, insertions, and deletions
	Slowest: Binary Search Tree, if unbalanced, can have runtime complexity of O(n), or an average-case of Theta(lg(n))

2. Observed (real) Performance: The results match my expectations. Hash Maps were the fastest, with an average execution time of 0.3474 seconds (347.4ms); Red-Black Trees were faster than BSTs, averaging 0.611 seconds (611ms); and Binary Search Trees were the slowest, averaging 1.5838 seconds.


3. No Unexpected Results, why?
	- Hash Maps: Theta((1) time complexity, efficiency comes from quick hash-based indexing
	- Red-Black Trees: Theta(lg(n)) consistent performance, making them faster than BSTs but slower than Hash Maps
	- Binary Search Trees: If the BST becomes unbalanced, operations take longer, it can degrade to (O(n))

================================================================================
