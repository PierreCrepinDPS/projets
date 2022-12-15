<template>
  <div class="detail">
    <div class="detail-view card">
      <div class="data card-body">
        <img class="image" v-bind:src="imgUrl+pokemonInfo.name+'.png'">
        <h2 class="card-title">{{pokemonInfo.name}}</h2>
        <div class="types">
          <div class="type" v-for="ty in pokemonInfo.types" v-bind:key="ty.slot" style="margin-bottom: 20px;">
            <span :class="ty.type.name">{{ty.type.name}}</span>
          </div>
        </div>
        <div class="abilities">
          <div class="ability" v-for="tal in pokemonInfo.abilities" v-bind:key="tal.ability.name" style="margin-bottom: 20px;">
            <span>{{tal.ability.name}}</span>
          </div>
        </div>
        <div class="property">
          <div class="left bold">Taille</div>
          <div class="right">{{pokemonInfo.height/10}} m</div>
        </div>
        <div class="property">
          <div class="left bold">Poids</div>
          <div class="right">{{pokemonInfo.weight/10}} kg</div>
        </div>
        <div class="evolves">
          <article class="evolve" v-for="pokemon in this.pokemonEvolve" v-bind:key="pokemon.name" :class="[(pokemonInfo.name===pokemon.name) ? 'ispokemon' : '']">
              <h3>{{pokemon.name}}</h3>
              <img v-bind:src="pokemon.url">
          </article>
      </div>
      </div>
      <button class="close" v-on:click="close()">Fermer</button>
    </div>
  </div>
</template>

<script>
import config from "../config/config.json"
import axios from '../../node_modules/axios'
export default {
  beforeMount(){
    axios.get(this.pokemonUrl.url)
      .then((e)=>{
        this.pokemonInfo = e.data;
        axios.get(this.pokemonInfo.species.url)
        .then((e)=>{
          axios.get(e.data.evolution_chain.url)
          .then((e)=>{
            var tempData = [];
            tempData = e.data.chain;
            let nbevolve = e.data.chain.evolves_to.length;
            while (nbevolve!==0){
              this.pokemonEvolve.push({
                "name": tempData.species.name,
                "url": this.imgUrl+tempData.species.name+'.png',
              });
              nbevolve = tempData.evolves_to.length
              tempData = tempData.evolves_to[0]
            }
          })
        })
      })
  },
  data(){
    return {
      pokemonInfo: [],
      imgUrl : config.IMG_URL,
      pokemonEvolve: [],
    }
  },
  props: {
    pokemonUrl: null,
  },
  methods: {
    close(){
      this.$emit('closeDetail');
    },
  }
};
</script>

<style lang="scss" scoped>
.type {
  .grass {
    background: rgb(3, 139, 44) !important;
  }
  .poison {
    background: rgb(74, 7, 105) !important;
  }
  .water {
    background: rgb(8, 135, 219) !important;
  }
  .dragon {
    background: rgb(27, 2, 68) !important;
  }
  .ice {
    background: rgb(78, 199, 255) !important;
  }
  .flying {
    background: rgb(145, 215, 255) !important;
  }
  .fire {
    background: rgb(238, 135, 17) !important;
  }
  .ghost {
    background: rgb(74, 52, 87) !important;
  }
  .fighting {
    background: rgb(122, 0, 0) !important;
  }
  .normal {
    background: rgb(104, 104, 104) !important;
  }
  .psychic {
    background: rgb(195, 0, 255) !important;
  }
  .bug {
    background: rgb(52, 87, 6) !important;
  }
  .dark {
    background: rgb(43, 43, 43) !important;
  }
  .steel {
    background: rgb(116, 116, 116) !important;
  }
  .fairy {
    background: rgb(248, 165, 237) !important;
  }
  .eletric {
    background: rgb(255, 217, 1) !important;
  }
  .rock {
    background: rgb(88, 95, 100) !important;
  }
  .ground {
    background: rgb(92, 70, 70) !important;
  }
}

.detail {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  position: fixed;
  top: 0;
  left: 0;
  padding: 90px 10px 10px;
  //width: calc(100% - 20px);
  // height: calc(100vh - 20px);
  width: 100%;
  height: 100vh;
  background: rgba(10, 7, 0, 0.562);
}
.detail-view {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 90%;
  padding: 50px 0 0;
  position: relative;

  max-width: 510px;

  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
}
.image {
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: -60px;
  width: 120px;
  height: 120px;
  background-color: #ffcb04;
  border-radius: 50%;
  overflow: hidden;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
}

h2 {
  text-transform: capitalize;
}

.data {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
  width: 100%;
  margin-bottom: 40px;
}
.property {
  width: 90%;
  max-width: 400px;
  border-bottom: 1px solid #ccc;
  margin-bottom: 10px;
}
.left {
  float: left;
}
.right {
  float: right;
}
h3 {
  width: 90%;
  max-width: 400px;
  border-bottom: 1px solid #ccc;
}

.types,
.abilities {
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
  width: 90%;
  max-width: 400px;
}
.type {
  // color: rgb(17, 67, 182);
  margin: 0 0 10px 0;
  padding: 5px 10px;
  font-weight: bold;
  font-size: 1rem;
  letter-spacing: 2px;
  text-transform: capitalize;
  span {
    color: #ffffff !important;
    padding: 10px 14px;
    border-radius: 29px;
  }
}
.ability {
  color: rgb(10, 119, 10);
  margin: 0 10px 10px 0;
  border-radius: 20px;
  padding: 5px 10px;
  font-weight: bold;
  font-size: 1rem;
  letter-spacing: 2px;
  text-transform: capitalize;
  word-wrap: none;
  word-break: keep-all;
  background-color: #ffffff;
  border: 3px solid;
}

.close {
  outline: none;
  border: none;
  border-radius: 5px;
  background-color: #c73015;
  color: #efefef;
  padding: 10px 20px;
  margin-bottom: 20px;
  font-size: 1.2rem;
  cursor: pointer;
}
i {
  font-size: 2rem;
  color: #efefef;
}
.bold {
  font-weight: bold;
}
</style>
