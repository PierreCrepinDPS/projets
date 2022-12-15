<template>
  <div class="container">
    <PokemonSearch @searchPokemonEmit="searchPokemonEmit"/>
    <PokemonDetail v-if="isVisible" :pokemonUrl="pokemon" @closeDetail="close"/>
    <PokemonList :searchdata="searchdata" @send_value_pokemon="pokemon_value"/>
  </div>
</template>

<script>
import PokemonDetail from "../components/PokemonDetail.vue";
import PokemonList from "../components/PokemonList.vue";
import PokemonSearch from "../components/PokemonSearch.vue";

export default {
  data(){
    return{
      pokemon: null,
      isVisible: false,
      searchdata: "",
    }
  },
  components: {
    PokemonDetail,
    PokemonList,
    PokemonSearch,
  },
  methods: {
    pokemon_value(e){
      this.pokemon = e;
      this.isVisible = true;
    },
    close(){
      this.isVisible = false;
    },
    searchPokemonEmit(e){
      this.searchdata = e;
    }
  }
};
</script>

<style lang="scss" scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  padding: 10px;
  width: calc(100% - 20px);
  min-height: calc(100vh - 20px);
  //background: radial-gradient(#ffbf0b, #e20000);

  font-family: "Acme", arial;
  font-size: 1rem;
  font-weight: normal;
}

h1 {
  color: #efefef;
}
</style>