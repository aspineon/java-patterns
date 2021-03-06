# Design Patterns

Even though the concept already existed before, design patterns where made popular in the computing world when the Gang of Four published _Design Patterns: Elements of Reusable Object-Oriented Software_ back on 1994.

During the years these have evolved, with programmers adapting them to their  needs or adding new ones to fit those spaces the existing ones couldn't cover, but the basic concept behind them is still the same: being a collection of good practices used to solve common problems.

---

## Why this library?

It came from a personal necessity. I found myself reusing various similar classes across projects, all of them patterns which would do better if they were grouped on a single library.

That's one of the reasons not all the existing patterns are included on this project, I've added just those I need.
	
But there are two other important reasons for it: first, there are some great third party libraries around which make it a waste trying to create classes for some common patterns, and also some of the more specific patterns have to be tailored for a specific problems, and so are not reusable.
	
After all, this project just aims to fill a gap I found on my own projects, and its development will be directed by this fact.

## The patterns in the library

The implementations are not following any concrete book or specification, they just mix what I need with a general idea of what they are expected to do.

- The [Command][docs-command], and the [CommandExecutor][docs-command], help to encapsulate code inside an object.
- The [Outputter][docs-outputter] allows writing for files, or just sending structures, without worrying about the actual implementation.
- The [Parser][docs-parser] transforms one object into another, keeping as much of the data intact as possible.
- The [Prototype][docs-prototype] permits creating an identical copy of a base template.
- The [Repository][docs-prototype] allows handling data persistence as if you were working on a collection.

For each of them, whenever possible, basic implementations have been included.

[docs-command]: ./command.html
[docs-outputter]: ./outputter.html
[docs-parser]: ./parser.html
[docs-prototype]: ./prototype.html
[docs-prototype]: ./repository.html