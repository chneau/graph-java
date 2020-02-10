.SILENT:
.ONESHELL:
.NOTPARALLEL:
.EXPORT_ALL_VARIABLES:
.PHONY:

NAME=$(shell basename $(CURDIR))

run: dldata

dldata:
	mkdir private
	curl -sSL https://download.geofabrik.de/europe/great-britain/scotland-latest.osm.pbf -o private/scotland.pbf
