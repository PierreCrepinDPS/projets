<template>
  <div id="app">
    <Pokedex />
  </div>
</template>

<script>
import Pokedex from "./components/Pokedex.vue";

export default {
  name: "app",
  components: {
    Pokedex,
  },
};
</script>

<style>
@import url("https://use.fontawesome.com/releases/v5.8.2/css/all.css");

body {
  margin: 0;
  padding: 0;
}
#app {
  background: radial-gradient(#ffbf0b, #e20000);
}

.evolves{
  padding-top: 20px;
  display: flex;
  gap: 10px;
  text-align: center;
}

.evolve{
  border-radius: 5px;
  box-shadow: 0 15px 30px rgb(0 0 0 / 20%), 0 10px 10px rgb(0 0 0 / 20%);
}

.evolve h3{
  margin: 15px 0 0;
  padding-bottom: 10px;
  width: 100%;
}

.ispokemon{
  background-color: rgb(0, 0, 0, 0.05);
}
</style>
