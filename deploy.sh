git pull

# Build and move api jar
./gradlew :api:jar
cp ./api/build/libs/api-1.0.8-SNAPSHOT.jar ./documentation/input.jar

# Generate documentation and move to book src
./gradlew :documentation:run
rm -r ./book/src
mkdir ./book/src
cp -a ./documentation/generated/. ./book/src

# Generate book
# shellcheck disable=SC2164
cd book
cargo install mdbook
mdbook build
rm -r /var/www/doc
mkdir /var/www/doc
cp -a ./book/. /var/www/doc