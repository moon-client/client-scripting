git pull

println "Deploying documentation"

# Build and move api jar
./gradlew :api:jar
cp ./api/build/libs/api-1.0-SNAPSHOT.jar ./documentation/input.jar

println "1/3 Done (API Jar)"

# Generate documentation and move to book src
./gradlew :documentation:run
rm -r ./book/src
mkdir ./book/src
cp -a ./documentation/generated/. ./book/src

println "2/3 Done (Documentation Generation)"

# Generate book
# shellcheck disable=SC2164
cd book
cargo install mdbook
mdbook build
cp -a ./book/. /var/www/doc

println "3/3 Done (Finished deploying documentation)"