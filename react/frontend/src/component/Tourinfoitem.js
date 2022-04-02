import React, {Fragment, useEffect, useState} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import './css/bootstrap.min.css';
import './css/style.css';
import axios from "axios";

function Tourinfoitem(props) {

    // 상세 페이지 출력 관광지 정보 
    const [tourSpot, setTourSpot] = useState({tag_prev:'', content:'', detail_content:''})

    const reqUrl = '/tour/lookup';

    const getInfoItem = async () => {
        await axios
            .get(reqUrl, {
                params: {
                    id: decodeURI(window.location.search.split('=')[1])
                }
            })
            .then((res) => setTourSpot(res.data));
    }

    // 처음 렌더링시 한번 실행되는 함수
    useEffect(() => {
        getInfoItem();
    }, [])

    const tourSpotRender = () => {
        const result = [];
        result.push(
            <Fragment>
            <div class="mb-5">

                <p class="h4">상세정보</p>
                <p>{tourSpot.content}</p>
            </div>
            </Fragment>
        );
        return result;
    };


    return (
        <Fragment>
            {tourSpotRender()}
        </Fragment>
    );
}

export default Tourinfoitem;