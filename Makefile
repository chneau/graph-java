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

dlgtfs:
	mkdir private
	curl -sSL https://raw.githubusercontent.com/danbillingsley/TransXChange2GTFS/master/Y_GTFS.zip -o private/leeds.gtfs
