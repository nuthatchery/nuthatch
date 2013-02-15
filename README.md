## Nuthatch/J

[Nuthatch/J](http://nuthatchery.org/) is a library for software
transformation and analysis, based on tree walking.

Transformations are described as walks that proceed in 
programmer-defined steps. Each step may which may observe
join points of a traversal, and affect state associated with
the walk and also rewrite the walked tree. Each step ends by
walking to a different node in the tree, following the
tree branches.

This package contains the core Nuthatch/J library. You may also
want to interface with either Stratego/XT or Rascal:

1. [Nuthatch/J+Stratego](https://github.com/nuthatchery/nuthatch-stratego)
2. [Nuthatch/J+Rascal](https://github.com/nuthatchery/nuthatch-rascal)

## Documentation

Documentation is available at the [Nuthatch website](http://nuthatchery.org/docs/).

## Warning: Experimental Software!

Nuthatch/J is currently highly experimental, and the API is changing all the time.

## Source code and bug reports

The source code is hosted on [GitHub](https://github.com/nuthatchery/nuthatch).
Please file bugs in the [GitHub Issue Tracker](https://github.com/nuthatchery/nuthatch/issues).

## License
  Copyright © 2013 Anya Helene Bagge
  
  This library is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as
  published by the Free Software Foundation, either version 3 of the
  License, or (at your option) any later version.

  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with this library.  If not, see <http://www.gnu.org/licenses/>.
