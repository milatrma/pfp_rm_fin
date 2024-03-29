import React from 'react'
import SignOut from "../buttons/SignOut";
import UserEmail from "../main_components/UserEmail";
import BackButton from "../buttons/BackButton";

const NavbarOtherPages = () => {

    return (<div className="bg-gray-800">
        <div className="h-16 px-8 flex items-center">
            <p className="text-white font-bold flex-auto">
                Personal Financing Platform
            </p>
            <div className="flex px-2">
                <UserEmail/>
            </div>
            <div className="flex px-2">
                <BackButton/>
            </div>
            <div className="flex px-2">
                <SignOut/>
            </div>
        </div>
    </div>);
};

export default NavbarOtherPages;