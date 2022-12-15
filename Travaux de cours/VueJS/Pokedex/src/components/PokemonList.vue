<template>
  <div class="list">
      <article v-for="pokemon in pokemonFiltered" v-bind:key="pokemon.name" v-on:click="send(pokemon)">
        <h3>{{pokemon.name}}</h3>
        <img v-bind:src='imgUrl+pokemon.name+".png"'>
      </article>
  </div>
</template>

<script>
import config from "../config/config.json"
import axios from '../../node_modules/axios'
export default {
  beforeMount(){
    // appel de l'api pour récuperer la liste des characters
    axios.get(config.API_URL+"/pokemon?limit=-1")
      .then((e)=>{
        // sur le retour on stock la data dans caracters
        this.pokemonList = e.data.results;
      })
  },
  data(){
    return{
      pokemonList: "",
      imgUrl : config.IMG_URL,
    }
  },
  props:{
    searchdata: String,
  },
  methods: {
    send(pokemon){
      this.$emit("send_value_pokemon", pokemon)
    }
  },
  computed:{
    pokemonFiltered: function(){
      if (this.searchdata!==""){
        return this.pokemonList.filter(el =>  el.name.toLowerCase().indexOf(this.searchdata.toLowerCase()) !== -1);
      }
      return this.pokemonList;
    }
  },
};
</script>

<style lang="scss" scoped>
.list {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  grid-gap: 10px;
  width: 100%;
  max-width: 510px;
}
article {
  height: 150px;
  background-color: #efefef;
  text-align: center;
  text-transform: capitalize;
  border-radius: 5px;
  cursor: pointer;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
}
h3 {
  margin: 0;
}
#scroll-trigger {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 150px;
  font-size: 2rem;
  color: #efefef;
}

img {
  width: 96px;
  height: 96px;
}

li{
  list-style: none;
}
</style>

