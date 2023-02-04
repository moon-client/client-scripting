git pull

# Build and move api jar
./gradlew :api:jar
cp ./api/build/libs/api-1.0.17-SNAPSHOT.jar ./generator/input.jar

# Generate documentation and move to book src
./gradlew :generator:run
rm -r ./book/src
mkdir ./book/src
cp -a ./generator/generated/. ./book/src

# Generate book
# shellcheck disable=SC2164
cd book
cargo install mdbook
mdbook build
rm -r /var/www/doc
mkdir /var/www/doc
cp -a ./book/. /var/www/doc
