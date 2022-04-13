import React, {Fragment} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import './component/css/bootstrap.min.css';
import './component/css/style.css';
import ListSearch from './component/ListSearch';
import Scrolltop from './component/Scrolltop';
import Footer from './component/Footer';
import TourlistitemReco from './component/TourlistitemReco';
import Map from './component/Map';
import ChatButton from './component/ChatButton';

function TouristAttractionListReco(props) {
    return (
        <Fragment>
            <ListSearch />
            <div class="container-xxl py-5 wow fadeInUp pdCon" data-wow-delay="0.1s">
                <div class="row g-5 maCon" style={{width:'100%'}}>
                    <div class="col-lg-5">
                        <TourlistitemReco/>
                    </div>
                    <Map/>
                </div>
            </div>
            <Scrolltop />
            <ChatButton/>
            <Footer />
        </Fragment>
    );
}

export default TouristAttractionListReco;