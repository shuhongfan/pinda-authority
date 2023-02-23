let RouterPlugin = function () {
  this.$router = null;
  this.$store = null;

};
RouterPlugin.install = function (vue, router, store) {
  this.$router = router;
  this.$store = store;
  this.$vue = new vue();

}