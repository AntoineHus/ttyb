# ttyB
[![License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat-square)](https://github.com/maxmouchet/ttyb/blob/master/LICENSE)
[![Travis Build Status](https://img.shields.io/travis/maxmouchet/ttyb.svg?style=flat-square)](https://travis-ci.org/maxmouchet/ttyb)

## Compilation

```sh
gradle installDist
```
The CLI executable will be found at `build/install/ttyb/bin/ttyb`.

## Usage

```sh
usage: ttyb [--help] [--verbose] [--browser=<browser>] [--username=<username>]
            [--password=<password>] <command> [<args>]

Available commands:
courses   Display registered courses and associated grades

Available browsers:
firefox   Use Firefox to parse internet pages
phantomjs Use PhantomJS to parse internet pages
```
