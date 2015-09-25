
[ "$1" = "-d" ] && showdeps=t

for f in */META-INF/MANIFEST.MF; do
	module=${f%%/*}
	current=`grep Bundle-Version $f | sed -e 's/.*: *//g'`
	if echo $current | grep -q \\.qualifier || [ ! -z "$qualify" ]; then
		q=.qualifier
	else
		q=
	fi

	echo $module $current

	if [ ! -z "$showdeps" ]; then
		grep " $module;bundle-version=\"[^\"]*\"" */META-INF/MANIFEST.MF
	fi
	
done
