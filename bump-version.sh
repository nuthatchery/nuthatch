

qualify=

if [ "$1" = "-q" ]; then
	qualify=t
	shift
fi

new=$1

if echo $new | grep -q \\.qualifier; then
	qualify=t
	new=${new%%.qualifier}
fi


for f in */META-INF/MANIFEST.MF; do
	module=${f%%/*}
	current=`grep Bundle-Version $f | sed -e 's/.*: *//g'`
	if echo $current | grep -q \\.qualifier || [ ! -z "$qualify" ]; then
		q=.qualifier
	else
		q=
	fi

	echo Bumping $module from $current to $new$q

	sed -i -e "s/^Bundle-Version: .*$/Bundle-Version: $new$q/g" $f
	for g in */META-INF/MANIFEST.MF; do
		sed -i -e "s/ $module;bundle-version=\"[^\"]*\"/ $module;bundle-version=\"$new\"/g" $g
	done
	
done
