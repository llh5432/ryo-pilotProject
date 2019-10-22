<template>
    <div>
        <b-form-group label="Selection mode:" label-cols-md="4">
            <b-form-select v-model="selectMode" :options="modes" class="mb-3"></b-form-select>
        </b-form-group>

        <b-table
                id="my-table"
                :per-page="perPage"
                :current-page="currentPage"
                small
                ref="selectableTable"
                selectable
                :select-mode="selectMode"
                selected-variant="active"
                :items="items"
                :fields="fields"
                @row-selected="onRowSelected"
                responsive="sm"
        >
            <!-- Example scoped slot for select state illustrative purposes -->
            <template v-slot:cell(selected)="{ rowSelected }">
                <template v-if="rowSelected">
                    <span aria-hidden="true">&check;</span>
                    <span class="sr-only">Selected</span>
                </template>
                <template v-else>
                    <span aria-hidden="true">&nbsp;</span>
                    <span class="sr-only">Not selected</span>
                </template>
            </template>
        </b-table>
        <p>
            <b-button size="sm" @click="selectAllRows">전체 선택</b-button>
            <b-button size="sm" @click="clearSelected">선택 해제</b-button>
            <b-button size="sm" @click="selectThirdRow">삭 제</b-button>
<!--            <b-button size="sm" @click="unselectThirdRow">Unselect 3rd row</b-button>-->
        </p>
        <b-pagination align="center"
                      v-model="currentPage"
                      :total-rows="rows"
                      :per-page="perPage"
                      aria-controls="my-table"
        ></b-pagination>
    </div>
</template>

<script>
    export default {
        name:"AdminMenuDeleteForm",
        data() {
            return {
                perPage: 3,
                currentPage: 1,
                modes: ['multi', 'single', 'range'],
                fields: ['selected', 'isActive', 'age', 'first_name', 'last_name'],
                items: [
                    { isActive: true, age: 40, first_name: 'Dickerson', last_name: 'Macdonald' },
                    { isActive: false, age: 21, first_name: 'Larsen', last_name: 'Shaw' },
                    { isActive: false, age: 89, first_name: 'Geneva', last_name: 'Wilson' },
                    { isActive: true, age: 38, first_name: 'Jami', last_name: 'Carney' }
                ],
                selectMode: 'multi',
                selected: []
            }
        },
        methods: {
            onRowSelected(items) {
                this.selected = items
            },
            selectAllRows() {
                this.$refs.selectableTable.selectAllRows()
            },
            clearSelected() {
                this.$refs.selectableTable.clearSelected()
            },
            selectThirdRow() {
                // Rows are indexed from 0, so the third row is index 2
                this.$refs.selectableTable.selectRow(2)
            }
            // unselectThirdRow() {
            //     // Rows are indexed from 0, so the third row is index 2
            //     this.$refs.selectableTable.unselectRow(2)
            // }
        },
        computed: {
            rows() {
                return this.items.length
            }
        }
    }
</script>