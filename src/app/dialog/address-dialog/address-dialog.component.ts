import { Component, Inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { AppConstants } from 'src/app/models/AppConstants';
import { Address } from 'src/app/models/address.model';
import { User } from 'src/app/models/user.model';
import { UserserviceService } from 'src/app/services/userservice.service';
import { UpdateAlertDialogComponent } from 'src/app/update-alert-dialog/update-alert-dialog.component';

@Component({
  selector: 'app-address-dialog',
  templateUrl: './address-dialog.component.html',
  styleUrls: ['./address-dialog.component.css']
})
export class AddressDialogComponent {
  editData: any;
  inputData !: any;
  isUpdateFlag !: boolean
  levels = AppConstants.LEVELS
  isUpdateButtonShow: boolean = true
  isSaveButtonShow: boolean = false
  headerTitleText: String = 'CREATE'
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialog: MatDialog,private dialogRef: MatDialogRef<AddressDialogComponent>, private formBuilder: FormBuilder, private userService: UserserviceService) { }

  ngOnInit(): void {
    this.inputData = this.data
    console.log('Recieved Data From Profile Component::', this.inputData)
    if (this.inputData.address) {
      this.myAddressForm.setValue({
        addressId: this.inputData.address.addressId,
        addressType: this.inputData.address.addressType,
        town: this.inputData.address.town,
        city: this.inputData.address.city,
        district: this.inputData.address.district,
        state: this.inputData.address.state,
        country: this.inputData.address.country,
        pin: this.inputData.address.pin
      })
    }
    if (this.data.isUpdateCreateFlag) {
      this.isUpdateButtonShow = false
      this.headerTitleText = 'UPDATE'
      this.isSaveButtonShow = true
    }
  }


  addressType=AppConstants.ADDRESS_TYPE

  setPopUpData() {
    this
  }

  closeAddressPopUp() {
    this.dialogRef.close(this.inputData);
  }

  myAddressForm = this.formBuilder.group({
    addressId: this.formBuilder.control(0),
    addressType: this.formBuilder.control(''),
    town : this.formBuilder.control(''),
    city: this.formBuilder.control(''),
    district : this.formBuilder.control(''),
    state : this.formBuilder.control(''),
    country : this.formBuilder.control(''),
    pin : this.formBuilder.control(''),
  })

  openAddressUpdateAlertPopUp(){
    const _popUpUpdateData = this.dialog.open(UpdateAlertDialogComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '1000ms',
      data: { triggerFor: 'address' }
    });
    _popUpUpdateData.afterClosed().subscribe(item => {
      if (item) {
        this.updateAddressData()
      } else {
        console.log("Not Updating..!")
      }
    })
  }

  updateAddressData() {
    console.log("Before address data::", this.inputData, "\nUpdating address data::", this.myAddressForm.value)
    //  Creating request body for address update
    const existingAddress = this.inputData as Address
    console.log("Existing Address :-",existingAddress)
    const updatedAddress = this.myAddressForm.value
    console.log("Updated Address :-",updatedAddress)

    const apiRequestBody = {
      "user": this.inputData.user as User,
      "address": this.myAddressForm.value as Address,
    }
    console.log("App Request Body For Update Address::", apiRequestBody)
    //  Update call for address
    const responce = this.userService.updateUserSingleAddress(apiRequestBody)
    responce.subscribe(result => {
      console.log(result)
    }, error => {
      console.log(error)
    })

    this.dialogRef.close(this.myAddressForm);
  }

  createNewAddressData() {
    console.log("Creating address data::", this.myAddressForm.value)
    //  Creating request body for address update
    console.log(this.myAddressForm.value)
    const apiRequestBody = {
      "user": this.inputData.user as User,
      "address": this.myAddressForm.value as Address,
    }
    console.log("App Request Body For Create New SKILL::", apiRequestBody)
    //  Create call for address
    const responce = this.userService.createUserAddress(apiRequestBody)
    responce.subscribe(result => {
      console.log(result)
    }, error => {
      console.log(error)
    })

    this.dialogRef.close(this.myAddressForm);
  }
}


